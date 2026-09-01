# ==========================================
# Stage 1: Build & Package with Maven
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy POM and download dependencies to optimize Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build production artifact
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Minimal Production Runtime
# ==========================================
FROM eclipse-temurin:17-jre-jammy AS runtime

# Metadata labels
LABEL maintainer="hotel-devops@grandhorizon.com"
LABEL description="Hotel Billing System containerized with Spring Boot"
LABEL version="1.0.0"

# Install curl for container health check
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

# Security: Create non-root system user and group
RUN groupadd -r spring && useradd -r -g spring -s /bin/false spring

# Set working directory
WORKDIR /app

# Copy fat JAR from builder stage
COPY --from=builder /app/target/hotel-billing-system-1.0.0.jar app.jar

# Set ownership
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring:spring

# Expose web application port
EXPOSE 8080

# Health check configuration
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Environment variables with sensible JVM defaults
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Launch the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
