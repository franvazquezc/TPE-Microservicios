package com.tudai.arquitecturasweb.microserviciousuario.service;

import com.tudai.arquitecturasweb.microserviciousuario.dto.RespuestaAPI;
import com.tudai.arquitecturasweb.microserviciousuario.feignClient.CuentaFeignClient;
import com.tudai.arquitecturasweb.microserviciousuario.feignClient.GroqClient;
import com.tudai.arquitecturasweb.microserviciousuario.feignClient.ViajeFeignClient;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IAService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroqClient groqChatClient;

    @Autowired
    private CuentaFeignClient cuentaFeignClient;

    @Autowired
    private ViajeFeignClient viajeFeignClient;

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
            String sqlTablas = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE()";
            List<Object> tablas = entityManager
                    .createNativeQuery(sqlTablas)
                    .getResultList();

            for (Object tablaObj : tablas) {
                String nombreTabla = tablaObj.toString();
                esquema.append("TABLE: ").append(nombreTabla).append("\n");

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
            String promptCompleto = construirPromptConEsquema(prompt);
            log.info("Prompt enviado a Groq: {}", promptCompleto);

            String respuestaIA = groqChatClient.preguntar(promptCompleto);
            log.info("Respuesta de Groq: {}", respuestaIA);

            // 3. Extraer y ejecutar SQL (OPCIONAL)
            List<?> resultadoBD = new ArrayList<>();
            String sqlEjecutado = null;
            try {
                String sql = extraerConsultaSQL(respuestaIA);
                sqlEjecutado = sql;
                resultadoBD = ejecutarSQL(sql);
                log.info("Resultados de BD: {} registros", resultadoBD.size());
            } catch (IllegalArgumentException e) {
                log.warn("No se encontró SQL válido en la respuesta de Groq");
            }

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
            respuestaFinal.put("sqlEjecutado", sqlEjecutado);
            respuestaFinal.put("endpointsLlamados", endpointsValidos);
            respuestaFinal.put("respuestaIA", respuestaIA);

            return ResponseEntity.ok(
                    new RespuestaAPI<>(true, "Prompt procesado exitosamente", respuestaFinal)
            );

        } catch (SecurityException e) {
            log.error("Error de seguridad: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new RespuestaAPI<>(false, "Error de seguridad: " + e.getMessage(), null));

        } catch (Exception e) {
            log.error("Error general: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaAPI<>(false, "Error al procesar el prompt: " + e.getMessage(), null));
        }
    }

    private String construirPromptConEsquema(String promptUsuario) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Eres un experto en consultas SQL y APIs REST.\n\n");

        prompt.append("=== ESQUEMA DE BASE DE DATOS DISPONIBLE ===\n");
        prompt.append(CONTEXTO_SQL);
        prompt.append("\n\n");

        prompt.append("=== MICROSERVICIOS DISPONIBLES ===\n");
        prompt.append("- cuentaFeignClient.getUsuariosByCuenta(1234) → Obtiene usuarios en una cuenta con cierto ID\n");
        prompt.append("- viajeFeignClient.getCantidadViajesUsuario(5, 2025-01-01T00:00:00Z, 2025-12-31T23:59:59Z) → Obtiene cantidad de viajes de un usuario entre fechas\n");

        prompt.append("\n=== INSTRUCCIONES ===\n");
        prompt.append("1. Responde ÚNICAMENTE con SQL válido O llamadas a microservicios\n");
        prompt.append("2. Termina cada SQL con punto y coma (;)\n");
        prompt.append("3. Usa SOLO los métodos listados arriba\n");
        prompt.append("4. Formato de respuesta: incluye el SQL o las llamadas a servicios que necesites\n");

        prompt.append("\n=== PREGUNTA DEL USUARIO ===\n");
        prompt.append(promptUsuario);

        return prompt.toString();
    }

    private String extraerConsultaSQL(String respuestaIA) {
        Pattern SQL_ALLOWED = Pattern.compile(
                "(?i)(SELECT|INSERT|UPDATE|DELETE)\\s+.*?;",
                Pattern.DOTALL
        );

        Matcher matcher = SQL_ALLOWED.matcher(respuestaIA);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No se encontró SQL válido en la respuesta");
        }

        String sql = matcher.group().trim();

        Pattern SQL_FORBIDDEN = Pattern.compile(
                "(?i)(DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE|EXEC|EXECUTE|CALL)",
                Pattern.DOTALL
        );

        if (SQL_FORBIDDEN.matcher(sql).find()) {
            throw new SecurityException("SQL contiene operaciones peligrosas: " + sql);
        }

        return sql;
    }

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

    private List<String> extraerEndpointsValidos(String respuestaIA) {
        List<String> endpoints = new ArrayList<>();

        // Patrón para llamadas a cuentaFeignClient y viajeFeignClient
        Pattern ENDPOINT_ALLOWED = Pattern.compile(
                "(cuentaFeignClient\\.getUsuariosByCuenta\\(\\d+\\)|" +
                        "viajeFeignClient\\.getCantidadViajesUsuario\\(\\d+,\\s*[^,]+,\\s*[^)]+\\))"
        );

        Matcher matcher = ENDPOINT_ALLOWED.matcher(respuestaIA);
        while (matcher.find()) {
            String endpoint = matcher.group().trim();
            endpoints.add(endpoint);
            log.info("Endpoint válido encontrado: {}", endpoint);
        }

        return endpoints;
    }

    private Object ejecutarEndpointValidado(String endpoint) {
        try {
            if (endpoint.contains("cuentaFeignClient.getUsuariosByCuenta(")) {
                Long idCuenta = extraerParametroLong(endpoint);
                return cuentaFeignClient.getUsuariosByCuenta(idCuenta);
            }

            if (endpoint.contains("viajeFeignClient.getCantidadViajesUsuario(")) {
                // Extraer: viajeFeignClient.getCantidadViajesUsuario(5, 2025-01-01T00:00:00Z, 2025-12-31T23:59:59Z)
                Long idUsuario = extraerParametroLong(endpoint);
                List<String> instants = extraerParametrosInstant(endpoint);

                if (instants.size() < 2) {
                    throw new IllegalArgumentException("Se necesitan 2 fechas en formato ISO");
                }

                Instant desde = Instant.parse(instants.get(0));
                Instant hasta = Instant.parse(instants.get(1));

                return viajeFeignClient.getCantidadViajesUsuario(idUsuario.intValue(), desde, hasta);
            }

            throw new IllegalArgumentException("Endpoint no permitido: " + endpoint);

        } catch (Exception e) {
            log.error("Error ejecutando endpoint {}: {}", endpoint, e.getMessage());
            throw new RuntimeException("Error en microservicio: " + e.getMessage(), e);
        }
    }

    private Long extraerParametroLong(String endpoint) {
        Pattern pattern = Pattern.compile("\\((\\d+)");
        Matcher matcher = pattern.matcher(endpoint);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        throw new IllegalArgumentException("No se encontró parámetro numérico en: " + endpoint);
    }

    private List<String> extraerParametrosInstant(String endpoint) {
        List<String> instants = new ArrayList<>();

        // Patrón para fechas ISO: YYYY-MM-DDTHH:MM:SSZ
        Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)");
        Matcher matcher = pattern.matcher(endpoint);

        while (matcher.find()) {
            instants.add(matcher.group(1));
        }

        return instants;
    }
}

