FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
EXPOSE 8080
COPY target/*.jar webcrawler.jar
ENTRYPOINT ["java","-jar","/webcrawler.jar"]
