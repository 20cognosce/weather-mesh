kubectl apply -f weather/k8s/deployment.yaml
kubectl apply -f weather/k8s/service.yaml

kubectl apply -f dictionary/k8s/deployment.yaml
kubectl apply -f dictionary/k8s/service.yaml

kubectl apply -f circuit-breaker/k8s/deployment.yaml
kubectl apply -f circuit-breaker/k8s/service.yaml

kubectl apply -f istio/gateway.yaml
kubectl apply -f istio/weather-virtual-svc.yaml
kubectl apply -f istio/dictionary-virtual-svc.yaml