FROM openjdk:11-jre-slim-buster
ENV USE_PROFILE=
ARG JAR_FILE=./build/libs/treenity-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${USE_PROFILE}","-jar","/app.jar"]
