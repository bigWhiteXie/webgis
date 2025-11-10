FROM openjdk:11-jre-slim

WORKDIR /app
COPY gis-admin/target/gis-admin.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
