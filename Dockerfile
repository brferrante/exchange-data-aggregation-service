FROM maven:3.8.3-openjdk-17
WORKDIR /app
COPY . .
RUN mvn clean package
EXPOSE 8090
CMD java -jar application.jar