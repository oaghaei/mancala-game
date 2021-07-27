FROM openjdk:11.0.12-jre-slim-buster
MAINTAINER "Omid Aghaei <omd.aghaei@gmail.com>"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mancala-game.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/mancala-game.jar"]