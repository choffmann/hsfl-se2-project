FROM openjdk:17-jdk-slim-buster AS builder
WORKDIR /app
COPY . .

RUN chmod +x gradlew
RUN ./gradlew clean
RUN ./gradlew frontend:build --stacktrace
COPY frontend/build/distributions/ backend/src/main/resources/static
RUN ./gradlew backend:bootJar -x test --stacktrace
RUN mkdir public
COPY backend/build/libs/ public/

WORKDIR /app/public
ENTRYPOINT java -jar "backend-0.0.1-SNAPSHOT.jar"
