ARG SERVICE=dictionary

FROM maven:3.8.5-openjdk-17 AS build-stage
ARG SERVICE
WORKDIR /weather-mesh
COPY pom.xml pom.xml
COPY /circuit-breaker ./circuit-breaker
COPY /dictionary ./dictionary
COPY /weather ./weather
RUN mvn clean install -DskipTests=true -pl :${SERVICE}

FROM eclipse-temurin:17.0.10_7-jre-jammy
ARG SERVICE
COPY --from=build-stage /weather-mesh/${SERVICE}/target/${SERVICE}-1.0.0.jar /opt/${SERVICE}.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/opt/${SERVICE}.jar"]