package com.tudai.arquitecturasweb.microserviciousuario.service;

import com.tudai.arquitecturasweb.microserviciousuario.dto.RespuestaAPI;
import com.tudai.arquitecturasweb.microserviciousuario.feignClient.GroqClient;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IAService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroqClient groqChatClient;

    private String CONTEXTO_SQL;

    private static final Logger log = LoggerFactory.getLogger(IAService.class);

    private static final Pattern SQL_ALLOWED =
            Pattern.compile("(?is)\\b(SELECT|INSERT|UPDATE|DELETE)\\b[\\s\\S]*?;");

    private static final Pattern SQL_FORBIDDEN =
            Pattern.compile("(?i)\\b(DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE)\\b");

    @PostConstruct
    public void inicializarEsquema() {
        this.CONTEXTO_SQL = this.extraerEsquemaDesdeBD();
        log.info("Esquema cargado exitosamente");
    }

    private String extraerEsquemaDesdeBD() {
        StringBuilder esquema = new StringBuilder();
        esquema.append("=== ESQUEMA DE LA BASE DE DATOS ===\n\n");

        try {
            // 1. Obtener nombres de tablas
            String sqlTablas = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE()";
            List<Object> tablas = entityManager
                    .createNativeQuery(sqlTablas)
                    .getResultList();

            // 2. Para cada tabla, obtener estructura
            for (Object tablaObj : tablas) {
                String nombreTabla = tablaObj.toString();
                esquema.append("TABLE: ").append(nombreTabla).append("\n");

                // Obtener columnas
                String sqlColumnas = "SELECT COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE, COLUMN_KEY FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + nombreTabla + "' AND TABLE_SCHEMA = DATABASE()";
                List<Object[]> columnas = entityManager
                        .createNativeQuery(sqlColumnas)
                        .getResultList();

                esquema.append("  Columnas:\n");
                for (Object[] col : columnas) {
                    esquema.append("    - ").append(col[0]).append(" (")
                            .append(col[1]).append(")");
                    if ("NO".equals(col[2])) esquema.append(" NOT NULL");
                    if ("PRI".equals(col[3])) esquema.append(" PRIMARY KEY");
                    esquema.append("\n");
                }

                esquema.append("\n");
            }

        } catch (Exception e) {
            log.error("Error extrayendo esquema de BD", e);
            esquema.append("Error: ").append(e.getMessage());
        }

        return esquema.toString();
    }

    @Transactional
    public ResponseEntity<?> procesarPrompt(String prompt) {
        try {
            // 1. Construir prompt con esquema (ahora ya está en CONTEXTO_SQL)
            String promptCompleto = construirPromptConEsquema(prompt);
            log.info("Prompt enviado a Groq: {}", promptCompleto);

            // 2. Enviar a Groq
            String respuestaIA = groqChatClient.preguntar(promptCompleto);
            log.info("Respuesta de Groq: {}", respuestaIA);

            // 3. Extraer y ejecutar SQL
            String sql = extraerConsultaSQL(respuestaIA);
            log.info("SQL extraído: {}", sql);
            List<?> resultadoBD = ejecutarSQL(sql);
            log.info("Resultados de BD: {} registros", resultadoBD.size());

            // 4. Extraer y ejecutar endpoints permitidos
            List<String> endpointsValidos = extraerEndpointsValidos(respuestaIA);
            Map<String, Object> resultadosServicios = new HashMap<>();

            for (String endpoint : endpointsValidos) {
                try {
                    Object resultado = ejecutarEndpointValidado(endpoint);
                    resultadosServicios.put(endpoint, resultado);
                    log.info("Endpoint ejecutado: {}", endpoint);
                } catch (Exception e) {
                    log.error("Error ejecutando endpoint {}: {}", endpoint, e.getMessage());
                    resultadosServicios.put(endpoint, "Error: " + e.getMessage());
                }
            }

            // 5. Combinar resultados
            Map<String, Object> respuestaFinal = new HashMap<>();
            respuestaFinal.put("baseDatos", resultadoBD);
            respuestaFinal.put("microservicios", resultadosServicios);
            respuestaFinal.put("sqlEjecutado", sql);
            respuestaFinal.put("endpointsLlamados", endpointsValidos);

            return ResponseEntity.ok(
                    new RespuestaApi<>(true, "Prompt procesado exitosamente", respuestaFinal)
            );

        } catch (SecurityException e) {
            log.error("Error de seguridad: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new RespuestaApi<>(false, "Error de seguridad: " + e.getMessage(), null));

        } catch (IllegalArgumentException e) {
            log.error("Error en validación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RespuestaApi<>(false, "Error en validación: " + e.getMessage(), null));

        } catch (Exception e) {
            log.error("Error general: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaApi<>(false, "Error al procesar el prompt: " + e.getMessage(), null));
        }
    }

    /**
     * Construye el prompt enriquecido con esquema y microservicios
     */
    private String construirPromptConEsquema(String promptUsuario) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Eres un experto en SQL y APIs REST.\n\n");

        prompt.append("=== ESQUEMA DE BASE DE DATOS DISPONIBLE ===\n");
        prompt.append(CONTEXTO_SQL);  // Aquí va el esquema extraído dinámicamente
        prompt.append("\n\n");

        prompt.append("=== MICROSERVICIOS DISPONIBLES ===\n");
        prompt.append("- userServiceClient.getUser(Long id) → Obtiene usuario por ID\n");
        prompt.append("- userServiceClient.getUserByEmail(String email) → Obtiene usuario por email\n");
        prompt.append("- orderServiceClient.getOrder(Long id) → Obtiene pedido por ID\n");
        prompt.append("- orderServiceClient.listOrders(Long userId) → Lista pedidos de un usuario\n\n");

        prompt.append("=== INSTRUCCIONES ===\n");
        prompt.append("1. Responde ÚNICAMENTE con SQL válido o llamadas a servicios\n");
        prompt.append("2. Termina cada SQL con punto y coma (;)\n");
        prompt.append("3. Usa SOLO los métodos listados arriba\n");
        prompt.append("4. Considera usar microservicios si necesitas información de otros sistemas\n\n");

        prompt.append("=== PREGUNTA DEL USUARIO ===\n");
        prompt.append(promptUsuario);

        return prompt.toString();
    }

    /**
     * Extrae la consulta SQL de la respuesta de Groq
     */
    private String extraerConsultaSQL(String respuestaIA) {
        // Patrón para SELECT, INSERT, UPDATE, DELETE
        Pattern SQL_ALLOWED = Pattern.compile(
                "(?i)(SELECT|INSERT|UPDATE|DELETE)\\s+.*?;",
                Pattern.DOTALL
        );

        Matcher matcher = SQL_ALLOWED.matcher(respuestaIA);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No se encontró SQL válido en la respuesta");
        }

        String sql = matcher.group().trim();

        // Validar que NO contenga operaciones peligrosas
        Pattern SQL_FORBIDDEN = Pattern.compile(
                "(?i)(DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE|EXEC|EXECUTE|CALL)",
                Pattern.DOTALL
        );

        if (SQL_FORBIDDEN.matcher(sql).find()) {
            throw new SecurityException("SQL contiene operaciones peligrosas: " + sql);
        }

        return sql;
    }

    /**
     * Ejecuta la consulta SQL en la BD
     */
    private List<?> ejecutarSQL(String sql) {
        try {
            jakarta.persistence.Query query = entityManager.createNativeQuery(sql);
            List<?> resultados = query.getResultList();

            log.info("SQL ejecutado correctamente. Registros afectados: {}", resultados.size());
            return resultados;

        } catch (Exception e) {
            throw new RuntimeException("Error ejecutando SQL: " + e.getMessage(), e);
        }
    }

    /**
     * Extrae endpoints válidos de la respuesta de Groq
     */
    private List<String> extraerEndpointsValidos(String respuestaIA) {
        List<String> endpoints = new ArrayList<>();

        // Patrón para llamadas permitidas
        Pattern ENDPOINT_ALLOWED = Pattern.compile(
                "(?i)userServiceClient\\.(getUser|getUserByEmail)\\([^)]*\\)|" +
                        "orderServiceClient\\.(getOrder|listOrders)\\([^)]*\\)"
        );

        Matcher matcher = ENDPOINT_ALLOWED.matcher(respuestaIA);
        while (matcher.find()) {
            String endpoint = matcher.group();

            // Validar que NO contenga operaciones peligrosas
            if (!endpoint.matches("(?i).*(delete|remove|drop|truncate).*")) {
                endpoints.add(endpoint);
                log.info("Endpoint válido encontrado: {}", endpoint);
            }
        }

        return endpoints;
    }

    /**
     * Ejecuta un endpoint validado
     */
    private Object ejecutarEndpointValidado(String endpoint) {
        try {
            // userServiceClient.getUser(123)
            if (endpoint.contains("userServiceClient.getUser(")) {
                Long id = extraerParametroLong(endpoint);
                return userServiceClient.getUser(id);
            }

            // userServiceClient.getUserByEmail('user@example.com')
            if (endpoint.contains("userServiceClient.getUserByEmail(")) {
                String email = extraerParametroString(endpoint);
                return userServiceClient.getUserByEmail(email);
            }

            // orderServiceClient.getOrder(456)
            if (endpoint.contains("orderServiceClient.getOrder(")) {
                Long id = extraerParametroLong(endpoint);
                return orderServiceClient.getOrder(id);
            }

            // orderServiceClient.listOrders(123)
            if (endpoint.contains("orderServiceClient.listOrders(")) {
                Long userId = extraerParametroLong(endpoint);
                return orderServiceClient.listOrders(userId);
            }

            throw new IllegalArgumentException("Endpoint no permitido: " + endpoint);

        } catch (Exception e) {
            log.error("Error ejecutando endpoint {}: {}", endpoint, e.getMessage());
            throw new RuntimeException("Error en microservicio: " + e.getMessage(), e);
        }
    }

    /**
     * Extrae parámetro numérico de una llamada a método
     */
    private Long extraerParametroLong(String endpoint) {
        Pattern pattern = Pattern.compile("\\((\\d+)");
        Matcher matcher = pattern.matcher(endpoint);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        throw new IllegalArgumentException("No se encontró parámetro numérico en: " + endpoint);
    }

    /**
     * Extrae parámetro string de una llamada a método
     */
    private String extraerParametroString(String endpoint) {
        Pattern pattern = Pattern.compile("\\('([^']*)'\\)|\\(\"([^\"]*)\"\\)");
        Matcher matcher = pattern.matcher(endpoint);

        if (matcher.find()) {
            String grupo1 = matcher.group(1);
            return grupo1 != null ? grupo1 : matcher.group(2);
        }

        throw new IllegalArgumentException("No se encontró parámetro string en: " + endpoint);
    }
}

