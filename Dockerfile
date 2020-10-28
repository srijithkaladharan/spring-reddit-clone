FROM openjdk:8-jdk-alpine
ADD target/demo-docker-app.jar demo-docker-app
EXPOSE 8080
ARG JAR_FILE=target/demo-docker-app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]