istioctl install -y --set meshConfig.accessLogFile=/dev/stdout
kubectl label namespace default istio-injection=enabled

kubectl apply -f istio/cluster-gateway.yaml
kubectl apply -f istio/weather-virtual-svc.yaml
kubectl apply -f istio/dictionary-virtual-svc.yaml
kubectl apply -f istio/circuit-breaker-virtual-svc.yaml
kubectl apply -f istio/auth-virtual-svc.yaml
kubectl apply -f istio/node-virtual-svc.yaml

kubectl apply -f /etc/istio-1.21.0/samples/addons/grafana.yaml
kubectl apply -f /etc/istio-1.21.0/samples/addons/prometheus.yaml
kubectl apply -f /etc/istio-1.21.0/samples/addons/kiali.yaml

# minikube tunnel

# minikube dashboard
# istioctl dashboard grafana
# istioctl dashboard prometheus
# istioctl dashboard kiali