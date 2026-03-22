FROM maven:3-eclipse-temurin-25 AS builder
# Definisci la directory di lavoro all'interno del container
WORKDIR /app

# Copia il pom e scarica le dipendenze (sfrutta la cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 3. Copiamo il codice sorgente
COPY src ./src
RUN mvn package



FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring


COPY --from=builder /app/target/inventory-service-0.0.1-SNAPSHOT.jar app.jar
RUN chown spring:spring app.jar
USER spring:spring

# Comando per avviare l'applicazione Spring Boot sulla porta 8080 di default
CMD ["java", "-jar", "app.jar"]