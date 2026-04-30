# -------- Stage 1: Build --------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy everything
COPY . .

# Build JAR (skip tests for faster build)
RUN mvn clean package -DskipTests


# -------- Stage 2: Run --------
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/StockFlow-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render uses dynamic PORT env)
EXPOSE 8080

# Run app
ENTRYPOINT ["java","-jar","/app/app.jar"]