FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/clientes-service-0.0.1.jar
COPY ${JAR_FILE} clientes-service.jar
RUN apt-get update && apt-get install -y netcat
ENTRYPOINT ["sh", "-c", "while ! nc -z config-sv 8888; do sleep 1; done && java -jar /clientes-service.jar"]