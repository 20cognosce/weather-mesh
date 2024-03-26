istioctl install -y
kubectl label namespace default istio-injection=enabled
kubectl apply -f /etc/istio-1.21.0/samples/addons/prometheus.yaml
kubectl apply -f /etc/istio-1.21.0/samples/addons/kiali.yaml
istioctl dashboard kiali