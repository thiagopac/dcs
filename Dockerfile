FROM openjdk:17-alpine3.14

VOLUME /tmp

COPY target/*.jar app.jar

RUN apk --update add fontconfig ttf-dejavu

ENTRYPOINT ["java","-jar","/app.jar"]