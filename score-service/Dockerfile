FROM openjdk:17
WORKDIR /app
COPY ./build/libs/*.jar score-service.jar
ENTRYPOINT ["java", "-jar", "score-service.jar"]