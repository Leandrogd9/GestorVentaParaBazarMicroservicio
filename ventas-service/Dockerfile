FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/ventas-service-0.0.1.jar
COPY ${JAR_FILE} ventas-service.jar
RUN apt-get update && apt-get install -y netcat
ENTRYPOINT ["sh", "-c", "while ! nc -z config-sv 8888; do sleep 1; done && java -jar /ventas-service.jar"]