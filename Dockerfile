# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copiamos TODA la estructura del proyecto primero
# (Esto es más seguro para proyectos multi-módulo de SAP CAP)
COPY . .

# 2. Construimos el módulo 'srv' directamente
# -pl srv: Project List 'srv'
# -am: Also Make (construye dependencias locales si existen)
RUN mvn clean package -DskipTests -pl srv -am

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
RUN apk add --no-cache dumb-init

# Seguridad: Usuario no root
RUN addgroup -g 1000 spring && adduser -u 1000 -G spring -s /bin/sh -D spring
WORKDIR /app

# 3. Copiamos el JAR generado en el stage anterior
# La ruta en CAP suele ser srv/target/*exec.jar o srv/target/*.jar
COPY --from=build /app/srv/target/*.jar app.jar

RUN chown -R spring:spring /app
USER spring:spring

ENV PORT=8080
EXPOSE $PORT

ENTRYPOINT ["dumb-init", "--"]
CMD ["java", "-Xmx512m", "-Dserver.port=${PORT}", "-jar", "app.jar"]