FROM openjdk:latest
VOLUME /tmp
COPY target/RewardsService-1.0-SNAPSHOT.jar RewardsService.jar
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=docker", "/RewardsService.jar" ]