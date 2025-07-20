FROM eclipse-temurin:21-jre-alpine

WORKDIR /popcorn-pal

COPY target/Popcorn-Pal-0.0.1-SNAPSHOT.jar popcorn-pal.jar

EXPOSE 8080

CMD [ "java", "-jar", "popcorn-pal.jar"]