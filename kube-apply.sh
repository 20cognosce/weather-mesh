kubectl apply -f weather/k8s/deployment.yaml
kubectl apply -f weather/k8s/service.yaml

kubectl apply -f dictionary/k8s/deployment.yaml
kubectl apply -f dictionary/k8s/service.yaml

kubectl apply -f circuit-breaker/k8s/deployment.yaml
kubectl apply -f circuit-breaker/k8s/service.yaml

kubectl apply -f auth/k8s/deployment.yaml
kubectl apply -f auth/k8s/service.yaml