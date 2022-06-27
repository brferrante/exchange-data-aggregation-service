FROM maven:3.8.6-eclipse-temurin-11-alpine AS build
WORKDIR /home/app
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /home/app/target/*.jar application.jar
EXPOSE 8090
CMD java -jar application.jar