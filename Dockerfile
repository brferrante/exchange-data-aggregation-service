FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /build
COPY . .
RUN mvn clean package

FROM openjdk:17-slim
WORKDIR  /app
COPY --from=build /build/target/*.jar application.jar
CMD java -jar application.jar --spring.profiles.active=docker