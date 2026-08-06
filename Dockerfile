# ═══════════════════════════════════════════════════════════════════════════
#  HMS Application — Multi-stage Dockerfile
#  Uses layered JAR for optimised caching and minimal final image size.
# ═══════════════════════════════════════════════════════════════════════════

# ── Stage 1: Build ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /build

# Copy Maven wrapper and pom first (layer cache for dependencies)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Download dependencies (cached layer if pom.xml unchanged)
RUN ./mvnw dependency:go-offline -q

# Copy source and build
COPY src ./src
RUN ./mvnw package -DskipTests -q

# Extract layered JAR
RUN java -Djarmode=layertools -jar target/*.jar extract

# ── Stage 2: Runtime ───────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine AS runtime

# Security: run as non-root
RUN addgroup -S hms && adduser -S hms -G hms
USER hms

WORKDIR /app

# Copy layered JAR layers (ordered least→most likely to change)
COPY --from=build /build/dependencies/ ./
COPY --from=build /build/spring-boot-loader/ ./
COPY --from=build /build/snapshot-dependencies/ ./
COPY --from=build /build/application/ ./

# Expose port
EXPOSE 8080

# JVM flags optimised for containers
ENV JAVA_OPTS="\
  -XX:+UseContainerSupport \
  -XX:MaxRAMPercentage=75.0 \
  -XX:+UseG1GC \
  -XX:+ExitOnOutOfMemoryError \
  -Djava.security.egd=file:/dev/./urandom \
  -Dspring.backgroundpreinitializer.ignore=true"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1
