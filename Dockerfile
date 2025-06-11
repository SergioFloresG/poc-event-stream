##
## Build stage
##
FROM eclipse-temurin:21-jdk-jammy AS build
ENV HOME=/usr/app
RUN mkdir -p "$HOME"
WORKDIR $HOME
COPY . $HOME
RUN --mount=type=cache,target=/root/.m2 /bin/sh ./mvnw -f "$HOME/pom.xml" clean package

##
## Package stage
##
FROM amazoncorretto:21-alpine-jdk
LABEL org.opencontainers.image.authors="Sergio Flores"
COPY --from=build /usr/app/target/*.jar runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "runner.jar"]
