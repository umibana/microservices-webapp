FROM openjdk:17-alpine
WORKDIR /app
COPY ./build/libs/*.jar installment-service.jar
ENTRYPOINT ["java", "-jar", "installment-service.jar"]