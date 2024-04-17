FROM node:18.14.0

WORKDIR /home/advertapp-ui
COPY ui .
RUN npm ci
RUN npm install -g serve

ARG BACKEND_BASE_URI
ENV REACT_APP_BACKEND_BASE_URI $BACKEND_BASE_URI

RUN npm run build

EXPOSE 3000
CMD [ "npx", "serve", "-n", "build" ]