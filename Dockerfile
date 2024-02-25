FROM maven:3.9.6-eclipse-temurin-17-alpine AS base

WORKDIR /app

COPY pom.xml /app

RUN mvn dependency:resolve

COPY . /app


FROM base as prod_build

RUN mvn clean

RUN mvn package -DskipTests -X


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=prod_build /storage-service/target/storage-service-*.jar /app

RUN ls

ENTRYPOINT ["/bin/sh", "-c", "java -jar storage-service-*.jar"]