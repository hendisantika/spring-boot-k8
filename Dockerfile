# --- STAGE 1 ---
FROM maven:3.9.11-eclipse-temurin-25 AS builder
LABEL authors="hendisantika"

# copy local code to the container image
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact
RUN mvn --batch-mode -f ./pom.xml package -DskipTests

# --- STAGE 2 ---
FROM eclipse-temurin:25-jdk

# copy the jar to the production image from the builder stage
COPY --from=builder /app/target/*.jar /app.jar

# run the web service on container startup
CMD ["/bin/sh", "-c", "java -jar /app.jar"]