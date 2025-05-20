# 🚀 Demo Project: Complete CI/CD Pipeline with EKS & AWS ECR Registry

## 🔧 Technologies Used

* **CI/CD**: Jenkins, Groovy (Declarative Pipeline)
* **Containerization**: Docker, Amazon Elastic Container Registry (ECR)
* **Orchestration**: Kubernetes (Amazon EKS)
* **Build Tools**: Java, Maven
* **Infrastructure**: AWS IAM, kubectl, AWS CLI
* **VCS**: GitHub
* **OS**: Linux (Jenkins container environment)

---

## 📦 Project Overview

This project demonstrates a full **CI/CD pipeline** for deploying a Java Maven application into a **Kubernetes cluster (EKS)** using Jenkins and **Amazon ECR** as a private container registry. The pipeline automates everything from version bumping, building, image publishing, to production deployment and Git commit syncing.

---

## 📜 CI/CD Pipeline Breakdown

### ✅ CI Stages

1. **Increment Version**

   * Automatically bumps the semantic version of the application in `pom.xml` using `maven-build-helper` and `versions:set`.
   * Sets a version tag combining Maven version and Jenkins build number (e.g. `1.1.12-9`).

2. **Build Artifact**

   * Uses Maven to compile the Java application and package it into a `.jar` file.

3. **Build & Push Docker Image**

   * Docker image is built from the compiled artifact.
   * Image is tagged and pushed to a **private AWS ECR** repository using Jenkins credentials and ECR login.

### ✅ CD Stages

4. **Deploy to Amazon EKS**

   * Uses `envsubst` to dynamically inject variables into Kubernetes YAML manifests (`deployment.yaml` and `service.yaml`).
   * Applies the manifests using `kubectl`, deploying the latest image to the EKS cluster.
   * Assumes valid `kubeconfig` is available in the Jenkins container with IAM-authenticated access.

5. **Commit Version Update**

   * Updates `pom.xml` with the new version.
   * Commits the change back to the GitHub repository using a GitHub Personal Access Token (PAT).
   * Configures Git author identity in the Jenkins job before committing.

---

## 🔐 Authentication & Security

* **Amazon ECR**: Uses Jenkins `usernamePassword` credentials for ECR registry login via Docker CLI.
* **GitHub**: Uses a PAT stored in Jenkins credentials for authenticated Git push.
* **AWS**: Uses IAM credentials to authenticate and interact with the EKS cluster.

---

## 📁 Kubernetes Deployment Structure

* `kubernetes/deployment.yaml`: Defines deployment resource with `image: $DOCKER_REPO:$IMAGE_NAME`
* `kubernetes/service.yaml`: Exposes the app using a `LoadBalancer` service (EKS-compatible)

---

## 📌 Summary of Pipeline Stages

| Stage Name              | Type | Description                                               |
| ----------------------- | ---- | --------------------------------------------------------- |
| `increment version`     | CI   | Bump application version using Maven helper plugins       |
| `build app`             | CI   | Compile and package the Java Maven application            |
| `build image`           | CI   | Build Docker image and push to AWS ECR (private)          |
| `deploy to K8s EKS`     | CD   | Deploy new Docker image to AWS EKS via dynamic manifests  |
| `commit version update` | CD   | Push updated `pom.xml` with bumped version back to GitHub |

---

## 📒 Step-by-Step Execution Guide

### 1. Prepare Jenkins Environment

* Install required plugins: Pipeline, Git, Docker, Kubernetes CLI.
* Install tools in container: Docker, kubectl, aws CLI, aws-iam-authenticator.
* Configure credentials:

  * AWS ECR: `aws-ecr-credentials`
  * GitHub PAT: `github-credentials`
  * AWS IAM: `aws_access_key_id` / `aws_secret_access_key`

### 2. Configure Kubernetes Access

* Create an EKS cluster using `eksctl` and generate the kubeconfig.
* Copy kubeconfig into `/var/jenkins_home/.kube/config` inside Jenkins container.
* (Optional) Configure appropriate `imagePullSecrets` if needed.

### 3. Prepare Application Repository

* Java Maven project with `pom.xml`
* Dockerfile
* Kubernetes YAML files:

  * `kubernetes/deployment.yaml`
  * `kubernetes/service.yaml`
* `Jenkinsfile` in root directory with defined pipeline logic.

### 4. Pipeline Execution Flow

1. Jenkins pulls source from GitHub.
2. `increment version`: Maven updates version in `pom.xml`
3. `build app`: Builds `.jar` file
4. `build image`: Builds and pushes image to AWS ECR
5. `deploy to K8s EKS`: Uses `kubectl` to apply YAMLs to EKS
6. `commit version update`: Commits updated `pom.xml` to GitHub

### 5. Validate Deployment

* Run `kubectl get all` to verify running pods and services
* Access app via the EKS LoadBalancer external IP
