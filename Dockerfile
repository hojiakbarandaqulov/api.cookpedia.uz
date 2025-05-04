FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/app
ARG JAR_FILE=target/api_cookpedia-*.jar
COPY ${JAR_FILE} api_cookpedia.jar

EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar","api_cookpedia.jar"]


#FROM openjdk:17
#WORKDIR /api_cookpedia
#COPY target/api_cookpedia-0.0.1-SNAPSHOT.jar api_cookpedia.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/api_cookpedia/api_cookpedia.jar"]



