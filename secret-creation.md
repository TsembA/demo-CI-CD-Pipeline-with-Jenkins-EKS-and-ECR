# If you're using a private Docker Hub registry, Kubernetes needs credentials to pull the image. You do that by creating a Kubernetes secret of type docker-registry, then referencing it in your deployment.

kubectl create secret docker-registry my-registry-key \
--docker-server=docker.io \
--docker-username=<username> \
--docker-password=<password>