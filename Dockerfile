FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY Asuneventos-api/pom.xml .
RUN mvn dependency:go-offline -B
COPY Asuneventos-api/src ./src
RUN mvn package -DskipTests -B

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]