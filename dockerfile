FROM eclipse-temurin:11-jre

WORKDIR /app
COPY webgis.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]