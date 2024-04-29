echo starting the build of docker images...

# circuit-breaker stage
cp ./circuit-breaker/Dockerfile .
docker build . -t circuit-breaker-image
rm Dockerfile

# dictionary stage
cp ./dictionary/Dockerfile .
docker build . -t dictionary-image
rm Dockerfile

# weather stage
cp ./weather/Dockerfile .
docker build . -t weather-image
rm Dockerfile

# auth stage
cp ./auth/Dockerfile .
docker build . -t auth-image
rm Dockerfile

# ui stage
cp ./weather-mesh-ui/Dockerfile .
docker build . -t node-image
rm Dockerfile