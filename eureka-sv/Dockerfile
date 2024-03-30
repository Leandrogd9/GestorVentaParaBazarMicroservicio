FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/eureka-sv-0.0.1.jar
COPY ${JAR_FILE} app_eureka.jar
RUN apt-get update && apt-get install -y netcat
ENTRYPOINT ["sh", "-c", "while ! nc -z config-sv 8888; do sleep 1; done && java -jar /app_eureka.jar"]
