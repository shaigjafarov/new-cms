FROM openjdk:11.0.7-jdk

VOLUME /tmp

COPY build/libs/discovery-service.jar discovery-service.jar

ENTRYPOINT ["java","-jar","/discovery-service.jar"]