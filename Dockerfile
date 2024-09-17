##
## Build stage
##
FROM eclipse-temurin:17-jdk-jammy as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 /bin/sh ./mvnw -f $HOME/pom.xml clean package

##
## Package stage
##
FROM amazoncorretto:17-alpine-jdk
MAINTAINER mrgenis.com
COPY --from=build /usr/app/target/*.jar runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "runner.jar"]
