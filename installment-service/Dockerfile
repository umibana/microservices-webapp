FROM openjdk:17
WORKDIR /app
COPY ./build/libs/*.jar installment-service.jar
ENTRYPOINT ["java", "-jar", "installment-service.jar"]