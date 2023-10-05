FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY target/*.jar app.jar

RUN apk --update add fontconfig ttf-dejavu

ENTRYPOINT ["java","-jar","/app.jar"]