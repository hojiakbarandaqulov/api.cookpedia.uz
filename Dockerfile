FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/app
ARG JAR_FILE=target/api_cookpedia-*.jar
COPY ${JAR_FILE} api_cookpedia.jar

ENTRYPOINT ["java", "-jar","api_cookpedia.jar"]
