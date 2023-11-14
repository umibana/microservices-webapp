FROM openjdk:17
WORKDIR /app
COPY ./build/libs/*.jar config-service.jar
ENTRYPOINT ["java", "-jar", "config-service.jar"]