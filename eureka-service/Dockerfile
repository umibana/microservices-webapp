FROM openjdk:17
WORKDIR /app
COPY ./build/libs/*.jar eureka-service.jar
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]