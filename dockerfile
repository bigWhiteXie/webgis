FROM openjdk:11-jre-slim

WORKDIR /app
COPY app.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
