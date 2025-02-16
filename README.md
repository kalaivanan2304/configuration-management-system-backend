# Configuration Management System - User Service & Config Service

This project is a **backend spring boot based micro service**.
It provides **authentication, authorization, and configuration management** using **Spring Security, JWT, and MySQL**.

---

##  **Table of Contents**
- [Tech Stack](#-tech-stack)
- [Architecture Overview](#-architecture-overview)
- [Setup Instructions](#-setup-instructions)
- [API Documentation](#-api-documentation)
- [Postman Collection](#-postman-collection)

---

##  **Tech Stack**
- **Java 17**
- **Spring Boot 3.11**
- **Spring Security & JWT** (Authentication)
- **Spring Data JPA & MySQL** (Database)
- **Docker & Docker Compose** (Containerization)
- **JUnit & Mockito** (Testing)

---

## **Architecture Overview**
This project follows a **microservice architecture** where:

1. **User Service** → Handles authentication, user registration, and role management.
2. **Config Service** → Stores application settings and configurations.
3. **JWT Authentication** → Protects APIs with role-based access control.

---

##  **Setup Instructions**
### ** 1. Clone the Repository**
```sh
git clone https://github.com/your-repo/config-management-system.git

```
### **2. Run Application locally**
```
cd config-management-system
config-management-system> ./gradlew clean build

** To build with JUint **
config-management-system> ./gradlew clean build -x test

** To build image and run container on docker **
docker-compose build
docker-compose up -d

**Note : If user-service / config-service doesnt start up by default using
       docker-compose up, please start it separately using below commands.**
       
docker start configuration-management-system-backend-user-service-1
docker start configuration-management-system-backend-config-service-1
       
```

### 3. Configure Environment Variables
```
Update below db connection param in application.yml, if required.
username: {username}
password: {password}

Update jwt.secret-key in secret-config.properties. if required.
JWT_SECRET={your_secret_key}
```~~~~



##  API Documentation

[configuration_management_system_oas.yml](api-documentation%2Fconfiguration_management_system_oas.yml)

##  Postman Collection
[Configuration Management System.postman_collection.json](api-documentation%2FConfiguration%20Management%20System.postman_collection.json)