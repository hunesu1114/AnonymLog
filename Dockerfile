FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} anonymlog.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","anonymlog.jar"]