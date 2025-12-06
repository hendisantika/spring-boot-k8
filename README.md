# spring-boot-k8

A Spring Boot REST API application designed for Kubernetes deployment. This project demonstrates cloud-native
containerization and orchestration practices with a complete K8s setup for both the application and its PostgreSQL
database.

## Tech Stack

- **Java 25** with **Spring Boot 4.0.0**
- **Spring Data JPA** for database access
- **PostgreSQL 14** database
- **Maven 3.9.11** for build management
- **Docker** with multi-stage builds
- **Kubernetes** for orchestration

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

```json
POST /api/persons
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

### Prerequisites

- Java 25
- Maven 3.9.11+
- Docker
- Kubernetes cluster (minikube, kind, or cloud provider)

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

## Kubernetes Deployment

### Deploy to Kubernetes

Apply the configurations in order:

```bash
# Database setup
kubectl apply -f deployment/postgres-secret.yaml
kubectl apply -f deployment/postgres-config.yaml
kubectl apply -f deployment/postgres-init.yaml
kubectl apply -f deployment/postgres.yaml

# Application setup
kubectl apply -f deployment/web-app-config.yaml
kubectl apply -f deployment/web-app.yaml
kubectl apply -f deployment/web-ingress.yaml
```

### Verify Deployment

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

## Configuration

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
