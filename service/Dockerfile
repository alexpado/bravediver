ARG AUTHOR="alexpado"
ARG PROJECT="bravediver"

FROM gradle:8.14.2-jdk21-alpine AS build

WORKDIR /source
RUN git clone https://github.com/${AUTHOR}/${PROJECT}.git && \
    cd ${PROJECT} &&  \
    gradle clean build --no-daemon &&  \
    rm -rf /source/${PROJECT}/build/libs/*-plain.jar &&  \
    mv /source/${PROJECT}/build/libs/*.jar /app.jar &&  \
    rm -rf /source

FROM openjdk:21-bookworm as service
LABEL developer="alexpdo"
COPY --from=build /app.jar .
ENTRYPOINT ["java", "-Djava.security.egdfile:/dev/./urandom", "-jar", "app.jar"]
