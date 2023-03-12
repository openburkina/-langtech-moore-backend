FROM openjdk:11
MAINTAINER hubert.sawadogo
CMD mvnw -Pprod clean verify
VOLUME /tmp
EXPOSE 8081
ADD target/*.jar langtech.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/langtech.jar"]
