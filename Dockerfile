FROM openjdk:11.0.7-jdk
MAINTAINER Janaina Paula
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

