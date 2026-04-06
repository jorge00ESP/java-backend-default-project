# 🛡️ Java Spring Boot Microservices Template (v1.0.0)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.1-blue)](https://spring.io/projects/spring-cloud)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/technologies/downloads/#java21)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/License-MIT-purple)](LICENSE)

A high-performance, modular, and production-ready microservices architecture designed for modern cloud environments. This template leverages the latest Spring Boot technologies to provide a robust foundation for building scalable distributed systems.

---

## 🐳 Quick Start (Docker)

> The fastest way to run the full stack — no local JDK, Maven, or PostgreSQL required.

```bash
git clone https://github.com/jorge00ESP/java-backend-default-project.git
cd java-backend-default-project
docker compose up -d --build
```

Once all containers are healthy, the API Gateway is available at **`http://localhost:9000`**.

| Useful URL | Description |
| :--- | :--- |
| `http://localhost:9000` | API Gateway (entry point for all requests) |
| `http://localhost:8761` | Eureka Dashboard (service registry) |
| `http://localhost:8888` | Config Server |

---

## 🏛️ Architecture Overview

Our architecture follows the **Centralized Configuration & Service Discovery** pattern, ensuring seamless communication and management across the ecosystem.

```mermaid
graph TD
    Client[Client Request] -->|Port 9000| Gateway[API Gateway]
    Gateway -->|Discovery| Eureka[Eureka Server]
    Gateway -->|Route /api/auth/**| JWTService[JWT User Service]
    Gateway -->|Route /api/example/**| ExampleService[Example Service]

    JWTService -->|Registration| Eureka
    ExampleService -->|Registration| Eureka

    Gateway -.->|Fetch Config| ConfigService[Config Server]
    JWTService -.->|Fetch Config| ConfigService
    ExampleService -.->|Fetch Config| ConfigService

    JWTService -->|Persistence| DB[(PostgreSQL)]
    ExampleService -->|Persistence| DB
```

### 🧩 Core Components

| Service | Port | Description |
| :--- | :---: | :--- |
| **Eureka Server** | `8761` | Service Registry where all microservices register to enable discovery. |
| **Config Server** | `8888` | Central hub for external configuration, serving properties to all services. |
| **API Gateway** | `9000` | Unified entry point. Handles routing to downstream services. |
| **JWT User Service** | `8080` | Identity & Access Management (IAM): registration and token generation. |
| **Example Service** | `8081` | Example business service for demos and integration tests. |

---

## ⚡ Key Features

- **🚀 Cutting Edge Stack**: Built on Java 21, Spring Boot 4.0.5, and Spring Cloud 2025.1.1.
- **🐳 Docker Ready**: Full `docker-compose.yml` with health checks and automatic startup ordering.
- **🛡️ JWT Authentication**: Secure user management with authentication protocols and token-based security.
- **⚙️ Centralized Configuration**: Manage application settings in one place (`config-files/`).
- **🔍 Service Discovery**: Automatic registration and discovery using Netflix Eureka.
- **🔀 Smart Routing**: Spring Cloud Gateway for flexible API management.
- **🐘 Database Ready**: Pre-configured support for PostgreSQL with JPA/Hibernate.
- **🏗️ Clean Architecture**: Decoupled domain logic from infrastructure (Hexagonal/Ports & Adapters).

---

## 🛠️ Getting Started

### Option A — Docker Compose (recommended)

**Prerequisites:** Docker Desktop (or Docker Engine + Compose plugin).

```bash
# Build images and start all services
docker compose up -d --build

# Follow logs
docker compose logs -f

# Stop everything (keeps DB data)
docker compose down

# Stop and delete DB volume
docker compose down -v
```

> **First build note:** Maven downloads all dependencies inside the containers — this may take a few minutes. Subsequent builds use the Docker layer cache and are much faster.

### Option B — Local (mvn spring-boot:run)

**Prerequisites:** Java 21 JDK · Maven 3.8+ · PostgreSQL on port 5432 · Git

1. **Clone the repository**
   ```bash
   git clone https://github.com/jorge00ESP/java-backend-default-project.git
   cd java-backend-default-project
   ```

2. **Start the database**
   ```bash
   cd postgres-db-docker && docker compose up -d
   ```

3. **Start services in order** (wait ~10-15 s between each)

   > [!IMPORTANT]
   > **Order matters!** Each service depends on the previous one being fully registered.

   ```bash
   # Terminal 1
   cd eureka-service && ./mvnw spring-boot:run
   # Terminal 2
   cd config-service && ./mvnw spring-boot:run
   # Terminal 3
   cd api-gateway-service && ./mvnw spring-boot:run
   # Terminal 4
   cd jwt-user-service && ./mvnw spring-boot:run
   # Terminal 5
   cd example-service && ./mvnw spring-boot:run
   ```

---

## 🛡️ Security & Authentication

The **JWT User Service** manages authentication using a secure token-based approach.

### 🔑 Authentication Endpoints

All requests go through the **API Gateway** on port `9000`.

| Endpoint | Method | Description |
| :--- | :---: | :--- |
| `/api/auth/register` | `POST` | Create a new user account. |
| `/api/auth/login` | `POST` | Authenticate and receive a JWT token. |

**Request body** (both endpoints):
```json
{
  "username": "jorge",
  "password": "secret123"
}
```

**Register — example:**
```bash
curl -X POST http://localhost:9000/api/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"username\": \"jorge\", \"password\": \"secret123\"}"
```
```json
{ "status": 200, "message": "User registered successfully", "body": "jorge" }
```

**Login — example:**
```bash
curl -X POST http://localhost:9000/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\": \"jorge\", \"password\": \"secret123\"}"
```
```json
{ "status": 200, "message": "Login successful", "body": "eyJhbGciOiJIUzI1NiJ9..." }
```

Use the token from `body` in subsequent requests:
```bash
curl http://localhost:9000/api/example/category \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 🔌 Example Service Endpoints

Available through the API Gateway at `http://localhost:9000/api/example/...`:

| Endpoint | Method | Description |
| :--- | :---: | :--- |
| `/api/example/category` | `GET` | List all categories |
| `/api/example/category` | `POST` | Create a new category (body: `CategoryDto`) |
| `/api/example/furniture` | `GET` | List all furniture items |
| `/api/example/furniture/{id}` | `GET` | Get furniture by id |
| `/api/example/furniture` | `POST` | Create a furniture item (body: `FurnitureDtoRequest`) |
| `/api/example/furniture/price` | `PUT` | Update furniture price (body: `FurniturePriceDtoRequest`) |
| `/api/example/furniture/{id}` | `DELETE` | Delete furniture by id |

> These endpoints may require a valid JWT in the `Authorization` header depending on the active security profile.

---

## 🛠️ Error Handling

Unified response format for all APIs via `ApiResponse`:

```json
{
  "status": 200,
  "message": "Descriptive message",
  "body": {}
}
```

- **Domain errors**: throw `CustomException` with an appropriate HTTP status (4xx/5xx). The `GlobalExceptionHandler` catches it and builds the response.
- **Unexpected errors**: any uncaught exception returns `500 Internal Server Error` with a generic message (no stack traces exposed).

---

## 🐳 Docker — In Depth

### Startup order & health checks

```
postgres-db ──► eureka-service ──► config-service
                                         │
                       ┌─────────────────┼──────────────────┐
                       ▼                 ▼                  ▼
              api-gateway-service  jwt-user-service  example-service
```

Each service uses `depends_on: condition: service_healthy`. Health checks use `wget` against the `/actuator/health` endpoint (HTTP 200 = healthy).

### How `localhost` URLs are overridden in Docker

Spring Cloud Config properties **take precedence over OS environment variables** by default. Simple env var overrides (`SPRING_DATASOURCE_URL`, etc.) would be silently ignored. The solution used here is **Spring profile overlays**:

Every service sets `SPRING_PROFILES_ACTIVE=docker`. The Config Server then serves a **merged** result of the base file plus the docker-profile file:

```
config-files/jwt-user-service.yml          ← base (username, password, JPA, JWT…)
config-files/jwt-user-service-docker.yml   ← profile (DB URL, Eureka URL)
                  ↓ merged by Config Server
final config received by jwt-user-service
```

Only the properties that **change** between environments need to be in the `*-docker.yml` files. Everything else is inherited from the base.

| Profile file | Properties overridden |
| :--- | :--- |
| `api-gateway-service-docker.yml` | Route URIs (`localhost` → container names), Eureka URL |
| `jwt-user-service-docker.yml` | `spring.datasource.url`, Eureka URL |
| `example-service-docker.yml` | `spring.datasource.url`, Eureka URL |

> The `config-service` itself has `EUREKA_CLIENT_ENABLED=false` so it doesn't try to register with Eureka and its `/actuator/health` stays **UP** immediately.

### Verify the merged config served to a service

```bash
# Replace {service} and {profile} as needed
curl http://localhost:8888/jwt-user-service/docker
curl http://localhost:8888/example-service/docker
curl http://localhost:8888/api-gateway-service/docker
```

### Useful Docker commands

```bash
# Rebuild and restart a single service (e.g. after a code change)
docker compose up -d --build jwt-user-service

# Restart services that depend on config changes (no rebuild needed)
docker compose up -d --force-recreate jwt-user-service example-service api-gateway-service

# Stream logs for a specific service
docker compose logs -f jwt-user-service

# Check health status of all containers
docker compose ps
```

---

## 📁 Directory Structure

```text
├── api-gateway-service/        # Routing & Load Balancing
│   └── Dockerfile
├── config-service/             # Cloud Config Server
│   └── Dockerfile
├── eureka-service/             # Netflix Eureka Discovery
│   └── Dockerfile
├── jwt-user-service/           # Authentication & User Management
│   └── Dockerfile
├── example-service/            # Example business service
│   └── Dockerfile
├── common-security/            # Shared JWT security library
├── config-files/               # Centralized YAML configurations
│   ├── api-gateway-service.yml
│   ├── api-gateway-service-docker.yml   ← Docker profile override
│   ├── jwt-user-service.yml
│   ├── jwt-user-service-docker.yml      ← Docker profile override
│   ├── example-service.yml
│   └── example-service-docker.yml      ← Docker profile override
├── postgres-db-docker/         # Standalone PostgreSQL compose (local dev)
└── docker-compose.yml          ← Full stack orchestration
```

---

## 🔐 About the `common-security` Project

`common-security` is a shared library module that centralizes JWT security utilities reusable across microservices.

- **`JwtUtils`**: JWT generation and validation, extraction of username and roles.
- **`JwtAuthenticationFilter`**: reads the `Authorization: Bearer <token>` header, validates the token and populates the `SecurityContext`.
- **`SecurityAutoConfiguration`**: auto-configuration that provides `JwtUtils` and `JwtAuthenticationFilter` beans.

**Quick integration steps:**
1. Add `common-security` as a dependency in `pom.xml`.
2. Configure `jwt.secret` and `jwt.expiration` in your service's config file.
3. Register `JwtAuthenticationFilter` in the Spring Security filter chain for protected routes.

> The module declares `spring-boot-starter-security` and `spring-boot-starter-web` with `provided` scope — consuming services must declare those dependencies themselves.

---

**Last Updated:** April 6, 2026 | **Version:** 1.0.0  
*Maintained by jorge00ESP*
