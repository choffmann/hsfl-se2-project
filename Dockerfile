FROM openjdk:17-jdk-slim-buster

WORKDIR public/

COPY backend/build/libs/backend-0.0.1-SNAPSHOT.jar .

ENTRYPOINT java -jar backend-0.0.1-SNAPSHOT.jar