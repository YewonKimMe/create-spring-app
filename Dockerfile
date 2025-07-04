FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
# 작업 디렉토리 설정
WORKDIR /app
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application-prod.yml /app/src/main/resources/application-prod.yml
ENV TZ=Asia/Seoul
RUN apt-get update && apt-get install -y tzdata && rm -rf /var/lib/apt/lists/*
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]