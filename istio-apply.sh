istioctl install -y --set meshConfig.accessLogFile=/dev/stdout
kubectl label namespace default istio-injection=enabled
kubectl apply -f /etc/istio-1.21.0/samples/addons/grafana.yaml
kubectl apply -f /etc/istio-1.21.0/samples/addons/prometheus.yaml
kubectl apply -f /etc/istio-1.21.0/samples/addons/kiali.yaml

# minikube tunnel

# istioctl dashboard grafana
# istioctl dashboard prometheus
istioctl dashboard kiali