docker login

docker image tag circuit-breaker-image cognosce/circuit-breaker-image
docker image tag dictionary-image cognosce/dictionary-image
docker image tag weather-manager-image cognosce/weather-manager-image

docker push cognosce/circuit-breaker-image
docker push cognosce/dictionary-image
docker push cognosce/weather-manager-image