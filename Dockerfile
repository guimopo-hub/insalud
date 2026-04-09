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

# Buscamos el JAR que NO sea el .jar.original o el plano.
# En proyectos SAP CAP/Spring Boot, el ejecutable suele terminar en .jar o -exec.jar
# Esta línea busca el archivo más grande o el que corresponde al ejecutable:
COPY --from=build /app/srv/target/*.jar app.jar

RUN chown -R spring:spring /app
USER spring:spring

ENV PORT=8080
EXPOSE $PORT

ENTRYPOINT ["dumb-init", "--"]
CMD ["java", "-Xmx400m", "-Dserver.port=${PORT}", "-jar", "app.jar"]