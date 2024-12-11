FROM openjdk:17-jdk-slim

WORKDIR /app

ARG APP_JAR=target/*.jar

COPY ${APP_JAR} app.jar

RUN apt-get update && apt-get install -y iputils-ping net-tools telnet && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8084