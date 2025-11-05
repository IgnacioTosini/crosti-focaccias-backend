# =============================================================================
# STAGE 1: Build stage - Compilar la aplicación
# =============================================================================
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Información del mantenedor
LABEL maintainer="IgnacioTosini"

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de Maven y pom.xml primero (para aprovechar cache de Docker)
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
COPY mvnw.cmd ./

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Descargar dependencias (esto se cachea si no cambia el pom.xml)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# =============================================================================
# STAGE 2: Runtime stage - Imagen final ligera solo con JRE
# =============================================================================
FROM eclipse-temurin:17-jre-alpine AS runtime

# Información del mantenedor
LABEL maintainer="IgnacioTosini"

# Crear un usuario no-root para mayor seguridad (Alpine usa addgroup/adduser)
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar solo el JAR compilado desde el stage anterior
COPY --from=builder /app/target/crosti-focaccias-*.jar app.jar

# Cambiar al usuario no-root
USER appuser

# Exponer el puerto (Render usa la variable PORT)
EXPOSE 8080

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=production

# Comando para ejecutar la aplicación
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar target/crosti-focaccias-*.jar"]
