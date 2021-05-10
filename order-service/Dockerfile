FROM openjdk:11.0.7-jdk

VOLUME /tmp

COPY build/libs/order-service.jar order-service.jar

ENTRYPOINT ["java","-jar","/order-service.jar"]