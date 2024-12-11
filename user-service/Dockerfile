FROM openjdk:17-jdk-slim

WORKDIR /app

ARG APP_JAR=target/*.jar

COPY ${APP_JAR} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8082