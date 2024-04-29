FROM node:22.0.0

WORKDIR /weather-mesh/weather-mesh-ui
COPY /weather-mesh-ui/public ./public
COPY /weather-mesh-ui/src ./src
COPY /weather-mesh-ui/package.json ./package.json
COPY /weather-mesh-ui/package-lock.json ./package-lock.json

RUN npm ci

EXPOSE 3000
ENTRYPOINT npm start