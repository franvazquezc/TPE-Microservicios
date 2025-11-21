# Etapa 1: build con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY .. .
RUN mvn -pl microservicio-pago -am clean package -DskipTests

# Etapa 2: imagen final con OpenJDK 21
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/microservicio-pago/target/*.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "app.jar"]