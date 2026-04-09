# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# 1. Copiamos el POM padre y el POM del módulo srv para cachear dependencias
# Si tienes más módulos (ej. db, api), añádelos aquí también
COPY pom.xml .
COPY srv/pom.xml ./srv/

# 2. Descargamos dependencias (esto se cacheará si los pom.xml no cambian)
RUN mvn dependency:go-offline -B

# 3. Copiamos el código fuente de los módulos
COPY srv/src ./srv/src

# 4. Construimos el proyecto
# Usamos -pl srv -am para compilar específicamente el módulo del servidor y sus dependencias
RUN mvn clean package -DskipTests -pl srv -am

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine

# Instalamos dumb-init para gestión de señales
RUN apk add --no-cache dumb-init

# Usuario no-root por seguridad
RUN addgroup -g 1000 spring && adduser -u 1000 -G spring -s /bin/sh -D spring

WORKDIR /app

# 5. IMPORTANTE: Copiamos el JAR desde la carpeta target del módulo SRV
COPY --from=build /app/srv/target/*.jar app.jar

# Permisos para el usuario spring
RUN chown -R spring:spring /app

USER spring:spring

# Configuración de puerto
ENV PORT=8080
EXPOSE $PORT

ENTRYPOINT ["dumb-init", "--"]

# Ejecución de la App
CMD ["java", "-Xmx512m", "-Dserver.port=${PORT}", "-jar", "app.jar"]