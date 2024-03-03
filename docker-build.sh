echo starting the build of docker images...

# circuit-breaker stage
cp ./circuit-breaker/Dockerfile .
docker build . -t circuit-breaker-image
rm Dockerfile

# dictionary stage
cp ./dictionary/Dockerfile .
docker build . -t dictionary-image
rm Dockerfile

# weather-manager stage
cp ./weather-manager/Dockerfile .
docker build . -t weather-manager-image
rm Dockerfile
