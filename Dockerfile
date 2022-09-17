FROM openjdk:11-slim

EXPOSE 9090

COPY build/libs/dynamic-menu-0.0.1-SNAPSHOT.jar dynamic-menu.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","/dynamic-menu.jar"]