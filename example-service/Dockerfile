# ── Stage 1: Build ──────────────────────────────────────────────────────────
# Context: project root (required for common-security dependency)
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# 1. Install common-security into local Maven repo
COPY common-security/pom.xml ./common-security/pom.xml
COPY common-security/src     ./common-security/src
RUN mvn -f common-security/pom.xml install -DskipTests -q

# 2. Resolve example-service dependencies (layer cache)
COPY example-service/pom.xml ./example-service/pom.xml
RUN mvn -f example-service/pom.xml dependency:go-offline -q 2>/dev/null || true

# 3. Build example-service
COPY example-service/src ./example-service/src
RUN mvn -f example-service/pom.xml package -DskipTests -q

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/example-service/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

