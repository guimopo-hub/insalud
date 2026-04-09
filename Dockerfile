# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Construimos el proyecto asegurando que se genere el JAR ejecutable
# Usamos el comando de Spring Boot si es necesario, pero package suele bastar
# si el pom.xml tiene el spring-boot-maven-plugin
RUN mvn clean package -DskipTests -pl srv -am

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
RUN apk add --no-cache dumb-init

# Usuario de seguridad
RUN addgroup -g 1000 spring && adduser -u 1000 -G spring -s /bin/sh -D spring
WORKDIR /app

# Forzamos a copiar el JAR que genera Spring Boot (suele ser el más pesado)
COPY --from=build /app/srv/target/*.jar app.jar

# En lugar de confiar en el manifiesto interno (que está fallando),
# le diremos a Java exactamente cuál es la clase principal de SAP CAP / Spring Boot.
# SUSTITUYE "com.tu.paquete.Application" por la clase que tiene el @SpringBootApplication
CMD ["java", "-Xmx400m", "-cp", "app.jar:app.jar/lib/*", "org.springframework.boot.loader.JarLauncher"]

RUN chown -R spring:spring /app
USER spring:spring

ENV PORT=8080
EXPOSE $PORT

ENTRYPOINT ["dumb-init", "--"]
CMD ["java", "-Xmx400m", "-Dserver.port=${PORT}", "-jar", "app.jar"]