# Spring Boot Kubernetes Config

## Késako ?
Application demonstrating outsourced Spring Boot configuration in a Kubernetes deployment context

## Deploy and run the application in Kubernetes

### Prerequisites

- OS : Linux or MacOS
- kubectl
- [SDKMan][sdkman-doc-install]
- [Minikube][minikube-doc-install]
    - This is also possible with **Kind**: Load container image into Kind runtime container (`kind load docker-image <IMAGE_NAME> --name <KIND_CLUSTER_NAME>)
- [Docker][docker-doc-install]

### Build container image 🐳

```bash
## Configure your local environment to use the Docker daemon inside the Minikube instance
eval $(minikube -p minikube docker-env)

## Build container image (In the same terminal)
./mvnw spring-boot:build-image -DskipTests

## Or Use Directly Dockerfile
docker build -t nzuguem/hello-kubernetes-config .
```

### Deploy in Kubernetes ☸️

3 different ways to externalize Spring Boot configuration in Kubernetes :

- [Spring Cloud Kubernetes Config Client][spring-cloud-kubernetes-config] : Use ConfigMap PropertySource
    - manifests: `./k8s/k8s-config`
    - It implements a [hot refresh mechanism][spring-cloud-kubernetes-reload] for the injected configuration
    - Hot reload **WILL NOT WORK** for the [following configuration sources](https://github.com/spring-cloud/spring-cloud-commons/blob/4a1daae7f947deb5f59d42a14f36eba01a9aa556/spring-cloud-context/src/main/java/org/springframework/cloud/context/refresh/ContextRefresher.java#L58) :
      ```java
      protected Set<String> standardSources = new HashSet<>(
          Arrays.asList(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME,
                  StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                  StandardServletEnvironment.JNDI_PROPERTY_SOURCE_NAME,
                  StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME,
                  StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME, "configurationProperties"));
      ```
- [Loading ConfigMaps into Spring Boot Applications as Environment Variables][spring-boot-external-config-json]
    - manifests: `./k8s/json-config`
- [Loading ConfigMaps into Spring Boot Applications as Volumes][spring-boot-external-config-properties-file]
    - manifests: `./k8s/volume-config`

```bash
## Apply manifest for target configuration type
kubectl apply -f ./k8s/<k8s|json|volume>-config

## Get Minikube Service  URL
minikube service hello-<kubernetes|json|volume>-config --url

## In the new terminal
curl <MINIKUBE_SERVICE_URL>
```

### Clean 🧹

```bash
## Delete Kubernetes objects
kubectl delete -f ./k8s/<k8s|json|volume>-config
```

<!-- Links -->
[sdkman-doc-install]: https://sdkman.io/install
[minikube-doc-install]: https://minikube.sigs.k8s.io/docs/start
[docker-doc-install]: https://docs.docker.com/engine/install/
[spring-cloud-kubernetes-config]: https://docs.spring.io/spring-cloud-kubernetes/reference/property-source-config/configmap-propertysource.html
[spring-cloud-kubernetes-reload]: https://docs.spring.io/spring-cloud-kubernetes/reference/property-source-config/propertysource-reload.html
[spring-boot-external-config-json]: https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.application-json
[spring-boot-external-config-properties-file]: https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.files