FROM openjdk:17
WORKDIR /app
COPY ./build/libs/*.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]