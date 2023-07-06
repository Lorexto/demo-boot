FROM openjdk:11-ea-jre-slim
EXPOSE 8080
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
