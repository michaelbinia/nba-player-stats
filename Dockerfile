FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/reactive-nba-world-1.0.0.jar /app
ENTRYPOINT ["java", "-jar", "reactive-nba-world-1.0.0.jar"]
