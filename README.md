# Expense Tracker API

A REST API for tracking income and expenses with multi-tenant support.

## Features

- Multi-tenant architecture with tenant isolation
- Income and expense management
- Category management
- JWT-based authentication
- Redis caching
- PostgreSQL database with Flyway migrations
- OpenAPI/Swagger documentation

## Tech Stack

- Java 25
- Spring Boot 4.0.3
- Spring Security
- Spring Data JPA
- Spring Data Redis
- PostgreSQL
- Flyway
- Lombok
- MapStruct

## Getting Started

### Prerequisites

- Java 25
- PostgreSQL
- Redis

### Configuration

Configure the application via `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/expense_tracker
    username: postgres
    password: postgres
  data:
    redis:
      host: localhost
      port: 6379
```

### Build

```bash
./mvnw clean install
```

### Run

```bash
./mvnw spring-boot:run
```

### Test

```bash
./mvnw test
```

## API Documentation

Once running, access:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Actuator: http://localhost:8080/actuator

## Endpoints

### Authentication
- `POST /api/v1/auth/login` - Login and receive JWT token

### Categories
- `GET /api/v1/categories` - List categories
- `POST /api/v1/categories` - Create category
- `GET /api/v1/categories/{id}` - Get category
- `PUT /api/v1/categories/{id}` - Update category
- `DELETE /api/v1/categories/{id}` - Delete category

### Income
- `GET /api/v1/incomes` - List incomes
- `POST /api/v1/incomes` - Create income
- `GET /api/v1/incomes/{id}` - Get income
- `PUT /api/v1/incomes/{id}` - Update income
- `DELETE /api/v1/incomes/{id}` - Delete income

### Expenses
- `GET /api/v1/expenses` - List expenses
- `POST /api/v1/expenses` - Create expense
- `GET /api/v1/expenses/{id}` - Get expense
- `PUT /api/v1/expenses/{id}` - Update expense
- `DELETE /api/v1/expenses/{id}` - Delete expense

## Project Structure

```
src/main/java/com/example/demo/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── service/         # Business logic
├── repository/      # Data access (JPA)
├── model/           # Domain entities, DTOs, mappers
├── exception/       # Custom exceptions
├── security/        # Security configuration
└── util/            # Utility classes
```
