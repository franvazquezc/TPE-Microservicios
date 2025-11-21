# Etapa 1: build con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY .. .
RUN mvn -pl microservicio-monopatin -am clean package -DskipTests

# Etapa 2: imagen final con OpenJDK 21
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/microservicio-monopatin/target/*.jar app.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]