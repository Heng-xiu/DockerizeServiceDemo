FROM openjdk:8-jdk-alpine
Maintainer Sheu,Heng-Shiou <hs06573@uga.com>
VOLUME /tmp
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://spring-mongo:27017/demoDB","-jar","/app.jar"] 

