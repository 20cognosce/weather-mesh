ARG APP=weather

FROM maven:3.8.5-openjdk-17 AS build-stage
ARG APP
WORKDIR /weather-mesh
COPY pom.xml pom.xml
COPY /commons ./commons
COPY /circuit-breaker ./circuit-breaker
COPY /dictionary ./dictionary
COPY /weather ./weather
COPY /auth ./auth
RUN mvn clean install -DskipTests=true -pl :commons -pl :${APP}

FROM eclipse-temurin:17.0.10_7-jre-jammy
ARG APP
ENV APP_VAR=$APP
COPY --from=build-stage /weather-mesh/${APP_VAR}/target/${APP_VAR}-1.0.0.jar /opt/${APP_VAR}.jar
EXPOSE 8080
ENTRYPOINT java -jar -Dspring.profiles.active=prod -Duser.timezone=Europe/Moscow /opt/${APP_VAR}.jar