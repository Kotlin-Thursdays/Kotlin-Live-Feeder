FROM openjdk:8-jdk-alpine
MAINTAINER Amanda Hinchman-Dominguez <ahinchman1@gmail.com>

VOLUME /tmp
EXPOSE 8080

RUN mkdir /work
COPY . /work
WORKDIR /work
RUN /work/gradlew build
RUN mv /work/build/libs/*.jar /work/app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]