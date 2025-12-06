# spring-boot-k8

A Spring Boot REST API application designed for Kubernetes deployment. This project demonstrates cloud-native
containerization and orchestration practices with a complete K8s setup for both the application and its PostgreSQL
database.

## Spring Boot On Kubernetes

This project demonstrates usage of Spring Boot and PostgreSQL with local Kubernetes deployment using Minikube and
Docker.

### Why Kubernetes?

Kubernetes has revolutionized the way we deploy, scale, and manage containerized applications. It provides a powerful
and flexible platform for automating deployment, scaling, and management of containerized applications and services.
There are several key motivations for using Kubernetes:

1. **Container Orchestration:** Allows us to orchestrate containers efficiently. It abstracts away the complexities of
   container deployment, networking, and scaling, making it easier to manage and maintain our applications.

2. **Scalability and High Availability:** Enables us to scale our applications horizontally to handle increased loads
   and ensure high availability by automatically managing replicas and distributing them across nodes.

3. **Resource Utilization:** Optimizes resource utilization by intelligently scheduling containers based on available
   resources, making efficient use of the underlying infrastructure.

4. **Rolling Updates and Rollbacks:** Allows us to perform rolling updates seamlessly, ensuring that our applications
   are always available during the update process. In case of issues, it also supports easy rollbacks to a previous
   stable version.

5. **Declarative Configuration:** With Kubernetes, we define the desired state of our applications using YAML files,
   making it easier to manage and version control configurations.

### Why Minikube?

Minikube is a lightweight Kubernetes distribution designed to run locally for development and testing purposes. It is an
excellent choice for developers who want to experiment with Kubernetes without the complexity of setting up a
full-fledged cluster.

Installation steps can be found on this link: https://minikube.sigs.k8s.io/docs/start/

By combining Spring Boot, PostgreSQL, Kubernetes, and Minikube, we can build a robust and scalable application that can
be easily deployed to production Kubernetes clusters while benefiting from the simplicity and convenience of local
development using Minikube.

## Tech Stack

- **Java 25** with **Spring Boot 4.0.0**
- **Spring Data JPA** for database access
- **PostgreSQL 14** database
- **Maven 3.9.11** for build management
- **Docker** with multi-stage builds
- **Kubernetes** for orchestration
- **Minikube** for local development

## Prerequisites

Before getting started with the project, please ensure you have the following installed on your machine:

- Java Development Kit (JDK) 25 or higher
- Maven 3.9.11+
- Docker / Docker Desktop (for containerization purposes)
- Minikube (for local Kubernetes development)
- kubectl (Kubernetes command-line tool)

## Project Structure

```
spring-boot-k8/
├── src/main/java/id/my/hendisantika/springbootk8/
│   ├── SpringBootK8Application.java
│   ├── controller/
│   │   ├── PersonController.java
│   │   └── HealthController.java
│   ├── service/
│   │   └── PersonService.java
│   ├── repository/
│   │   └── PersonRepository.java
│   └── entity/
│       └── Person.java
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   └── application-prod.yml
├── deployment/
│   ├── web-app.yaml
│   ├── postgres.yaml
│   ├── postgres-secret.yaml
│   ├── postgres-config.yaml
│   ├── postgres-init.yaml
│   ├── web-app-config.yaml
│   └── web-ingress.yaml
├── http/
│   ├── health.http
│   └── person.http
├── Dockerfile
├── compose.yaml
└── pom.xml
```

## API Endpoints

### Health Check

| Method | Endpoint            | Description                       |
|--------|---------------------|-----------------------------------|
| GET    | `/api/health/check` | Returns application health status |

### Person Management

| Method | Endpoint            | Description             |
|--------|---------------------|-------------------------|
| GET    | `/api/persons`      | Retrieve all persons    |
| GET    | `/api/persons/{id}` | Retrieve a person by ID |
| POST   | `/api/persons`      | Create a new person     |

### Request/Response Examples

**Create Person:**

```http
POST /api/persons
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "age": 30
}
```

**Response:**

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "age": 30
}
```

## Getting Started

### Build the Application

```bash
# Using Maven wrapper
./mvnw clean package

# Skip tests
./mvnw clean package -DskipTests
```

### Docker Build

```bash
docker build -t spring-app:1.0 .
```

### Run Locally with Docker Compose

```bash
docker-compose up
```

## Deployment Specifications

All deployment specifications are located in the [deployment](/deployment) folder. The folder contains the following
specifications:

**Database Configuration:**

- **postgres-secret.yaml**: Contains secrets for PostgreSQL connection (username and password). All secrets must be
  base64 encoded.
- **postgres-init.yaml**: Contains initialization script for PostgreSQL database (database creation).
- **postgres-config.yaml**: Contains PostgreSQL service URL and PersistentVolumeClaim configuration.
- **postgres.yaml**: Deployment and service configuration for PostgreSQL database.

**Application Configuration:**

- **web-app-config.yaml**: Contains configuration variables for Spring Boot application (e.g., Spring active profiles).
- **web-app.yaml**: Deployment and service configuration for Spring Boot application.
- **web-ingress.yaml**: External Kubernetes integrated Load Balancer responsible for routing traffic to internal
  services.

## Running on Kubernetes with Minikube

### Start Minikube

First, start the Minikube local Kubernetes cluster:

```bash
minikube start
```

### Deploy the Application

After starting Minikube, apply the configuration files using `kubectl`:

```bash
# 1. Navigate to deployment folder
cd deployment/

# 2. Apply database configurations
kubectl apply -f postgres-secret.yaml
kubectl apply -f postgres-config.yaml
kubectl apply -f postgres-init.yaml
kubectl apply -f postgres.yaml

# 3. Build the Docker image
cd ../
docker build -t spring-app:1.0 .

# 4. Apply application configurations
cd deployment/
kubectl apply -f web-app-config.yaml
kubectl apply -f web-app.yaml
kubectl apply -f web-ingress.yaml
```

### Verify Deployment

Check the status of your pods:

```bash
kubectl get all
```

Or check individual resources:

```bash
kubectl get deployments
kubectl get services
kubectl get pods
```

### Access the Application

```bash
# Port forward for local testing
kubectl port-forward service/spring-service 8080:8080

# Access endpoints
curl http://localhost:8080/api/health/check
curl http://localhost:8080/api/persons
```

## Kubernetes Architecture

| Component             | Description                       |
|-----------------------|-----------------------------------|
| `spring-deployment`   | 2 replicas of the Spring Boot app |
| `spring-service`      | ClusterIP service on port 8080    |
| `postgres-deployment` | PostgreSQL 14 instance            |
| `postgres-service`    | Database service on port 5432     |
| `postgres-pv-claim`   | 4Gi persistent storage            |
| `my-app-ingress`      | NGINX ingress for external access |

## Configuration Profiles

### Development Profile

- Database: `jdbc:postgresql://localhost:5435/k8_sample_db`
- Hibernate DDL: `create-drop`
- SQL logging enabled

### Production Profile

- Database: Uses environment variables (`POSTGRES_HOST`, `DB_USR`, `DB_PSW`)
- Hibernate DDL: `update`
- SQL logging disabled

## Testing

HTTP test files are included for IDE testing:

- `http/health.http` - Health check tests
- `http/person.http` - Person CRUD operations

## License

This project is open source and available under the MIT License.
