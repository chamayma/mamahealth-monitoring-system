# MamaHealth Monitoring System - Backend

This is the Spring Boot backend for the MamaHealth Monitoring System, a platform dedicated to postpartum recovery and monitoring for mothers.

## Technologies Used
- Java
- Spring Boot
- Spring Security (JWT Authentication)
- PostgreSQL
- Hibernate / Spring Data JPA
- Maven

## Prerequisites
- Java 17 or higher
- PostgreSQL Server
- Maven

## Getting Started

### 1. Database Setup
Ensure you have PostgreSQL running. The application expects a database as configured in `src/main/resources/application.properties` (or `application.yml`).

### 2. Build the Project
You can build the project using the Maven wrapper:
```bash
./mvnw clean install
```

### 3. Run the Application
You can run the application locally using the following command:
```bash
./mvnw spring-boot:run
```
By default, the backend will start on `http://localhost:8080`.

## API Documentation
Once the application is running, you can access the Swagger UI documentation at:
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

## Features
- **User Authentication**: JWT-based login and registration (supporting `MOTHER` and `DOCTOR` roles).
- **Appointments**: Schedule and manage doctor appointments.
- **Monitoring**: Track postpartum recovery data.

## License
This project is licensed under the MIT License.
