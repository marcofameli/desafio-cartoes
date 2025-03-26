# Stage 1: Builder
FROM maven:3.8.4-openjdk-17-slim as builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-alpine

WORKDIR /app
COPY --from=builder /app/target/tecnico-0.0.1-SNAPSHOT.jar /app/tecnico-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "/app/tecnico-0.0.1-SNAPSHOT.jar"]