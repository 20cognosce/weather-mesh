docker login

docker image tag circuit-breaker-image cognosce/circuit-breaker-image
docker image tag dictionary-image cognosce/dictionary-image
docker image tag weather-image cognosce/weather-image
docker image tag auth-image cognosce/auth-image

docker push cognosce/circuit-breaker-image
docker push cognosce/dictionary-image
docker push cognosce/weather-image
docker push cognosce/auth-image