# 🛡️ Java Spring Boot Microservices Template (v1.1.0)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.1-blue)](https://spring.io/projects/spring-cloud)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/technologies/downloads/#java21)
[![License](https://img.shields.io/badge/License-MIT-purple)](LICENSE)

A high-performance, modular, and production-ready microservices architecture designed for modern cloud environments. This template leverages the latest Spring Boot technologies to provide a robust foundation for building scalable distributed systems.

---

## 🏛️ Architecture Overview

Our architecture follows the **Centralized Configuration & Service Discovery** pattern, ensuring seamless communication and management across the ecosystem.

```mermaid
graph TD
    Client[Client Request] -->|Port 8081| Gateway[API Gateway]
    Gateway -->|Discovery| Eureka[Eureka Server]
    Gateway -->|Route| JWTService[JWT User Service]
    
    JWTService -->|Registration| Eureka
    ConfigService -->|Registration| Eureka
    
    Gateway -.->|Fetch Config| ConfigService[Config Server]
    JWTService -.->|Fetch Config| ConfigService
    
    JWTService -->|Persistence| DB[(PostgreSQL)]
```

### 🧩 Core Components

| Service | Port | Description |
| :--- | :--- | :--- |
| **Eureka Server** | `8761` | Service Registry where all microservices "handshake" to enable discovery. |
| **Config Server** | `8888` | Central hub for external configuration, serving properties to all services. |
| **API Gateway** | `8081` | The unified entry point. Handles load balancing and intelligent routing. |
| **JWT User Service** | `8082` | Identity & Access Management (IAM) handling registration and token generation. |

---

## ⚡ Key Features

- **🚀 Cutting Edge Stack**: Built on Java 21, Spring Boot 4.0.5, and Spring Cloud 2025.1.1.
- **🛡️ JWT Authentication**: Secure user management with authentication protocols and token-based security.
- **⚙️ Centralized Configuration**: Manage application settings in one place (`config-files/`) without redeploying code.
- **🔍 Service Discovery**: Automatic registration and discovery using Netflix Eureka.
- **🔀 Smart Routing**: Spring Cloud Gateway for flexible, non-blocking API management.
- **🐘 Database Ready**: Pre-configured support for PostgreSQL with JPA/Hibernate.
- **🏗️ Clean Architecture**: Decoupled domain logic from infrastructure (Hexagonal/Ports & Adapters).

---

## 🛠️ Getting Started

### 📋 Prerequisites

- **Java 21 Development Kit (JDK)**
- **Maven 3.8.0+**
- **PostgreSQL** (running on port 5432)
- **Git**

### 🏁 Setup Guide

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/jorge00ESP/java-backend-default-project.git
    cd java-backend-default-project
    ```

2.  **Start Services in Order** (Crucial for discovery)

    > [!IMPORTANT]
    > **Order matters!** Wait approximately 10-15 seconds between starting each service.

    1.  **Eureka Discovery Service**
        ```bash
        cd eureka-service && ./mvnw spring-boot:run
        ```
    2.  **Config Management Service**
        ```bash
        cd config-service && ./mvnw spring-boot:run
        ```
    3.  **JWT User Service**
        ```bash
        cd jwt-user-service && ./mvnw spring-boot:run
        ```
    4.  **API Gateway Service**
        ```bash
        cd api-gateway-service && ./mvnw spring-boot:run
        ```

### 🔍 Verification Checklist

- [ ] **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761) (Check for all 3 clients UP)
- [ ] **Config Health**: [http://localhost:8888/actuator/health](http://localhost:8888/actuator/health)
- [ ] **Gateway Health**: [http://localhost:8081/actuator/health](http://localhost:8081/actuator/health)
- [ ] **Auth Check**: POST to `http://localhost:8081/api/auth/register` (through gateway)

---

## 🛡️ Security & Authentication

The **JWT User Service** manages authentication using a secure token-based approach.

### 🔑 Authentication Endpoints

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/api/auth/register` | `POST` | Create a new user account. |
| `/api/auth/login` | `POST` | Authenticate and receive a JWT token. |

> [!TIP]
> Use the Gateway port (`8081`) to access these endpoints. The gateway will automatically route the request to the `jwt-user-service` instance.

---

## 📁 Directory Structure

```text
├── api-gateway-service/   # Routing & Load Balancing
├── config-service/        # Cloud Config Server
├── eureka-service/        # Netflix Eureka Discovery
├── jwt-user-service/      # Authentication & User Management
├── config-files/          # Centralized YAML configurations
└── pom.xml                # Parent orchestration (if applicable)
```

---

## 📝 Roadmap & Future Enhancements

- [ ] **Dockerization**: Full `docker-compose` support for one-click deployment.
- [ ] **Logging & Observability**: Integration with Elastic Stack (ELK) or Prometheus/Grafana.
- [ ] **API Documentation**: Automated Swagger/OpenAPI 3 interface for all services.
- [ ] **CI/CD**: GitHub Actions workflows for automated testing and building.

---

## 🤝 Contributing

Contributions make the open-source community an amazing place to learn and build.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

---

## 📄 License

This project is licensed under the **MIT License**. See `LICENSE` for more information.

---

**Last Updated:** March 2026 | **Version:** 1.1.0  
*Maintained by jorge00ESP*
