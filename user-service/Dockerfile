FROM openjdk:17
WORKDIR /app
COPY ./build/libs/*.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]