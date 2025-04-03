#Dont hove javadoc, debug,...
FROM openjdk:17-jdk-slim

#Working folder in Container
WORKDIR /app

#Copy file .jar from folder target in project to folder app of container
ARG FILE_JAR=target/stepbystep-0.0.1-SNAPSHOT.jar
COPY ${FILE_JAR} edu-app.jar

#Expose Port
EXPOSE 8080

#Run App Spring Boot
ENTRYPOINT ["java", "-jar", "edu-app.jar"]