FROM maven:3.9.6-eclipse-temurin-17-alpine AS MAVEN_BUILD

RUN mkdir /storage-service

WORKDIR /storage-service

COPY . /storage-service

RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=MAVEN_BUILD /storage-service/target/storage-service-*.jar /app

RUN ls

ENTRYPOINT ["/bin/sh", "-c", "java -jar storage-service-*.jar"]