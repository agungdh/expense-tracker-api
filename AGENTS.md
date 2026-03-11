# AGENTS.md - Developer Guidelines

This document provides guidelines for agentic coding agents working in this repository.

## Project Overview

- **Project Name**: demo
- **Type**: Spring Boot 4.0.3 REST API
- **Language**: Java 25
- **Build Tool**: Maven (via `./mvnw` wrapper)
- **Database**: PostgreSQL with Flyway migrations
- **Cache**: Redis
- **Additional**: Spring Security, JPA, Validation, Actuator, SpringDoc OpenAPI

## Build Commands

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=DemoApplicationTests

# Run a single test method
./mvnw test -Dtest=DemoApplicationTests#contextLoads

# Skip tests during build
./mvnw clean install -DskipTests

# Package without tests
./mvnw package -DskipTests

# Run with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Code Style Guidelines

### General Principles

- Follow Spring Boot best practices and idioms
- Use Lombok to reduce boilerplate code
- Prefer constructor injection over field injection
- Use interfaces for services when appropriate
- Keep methods focused and single-responsibility

### Naming Conventions

- **Classes**: PascalCase (e.g., `UserService`, `ProductController`)
- **Methods**: camelCase (e.g., `findById`, `saveUser`)
- **Variables**: camelCase (e.g., `userId`, `productList`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Packages**: lowercase with dots (e.g., `com.example.demo.controller`)
- **Test Classes**: `<ClassName>Tests` or `<ClassName>IT` for integration tests

### Java Code Conventions

- **Indentation**: 4 spaces (no tabs)
- **Line Length**: Max 120 characters
- **Imports**: Organize: static imports, then java, javax, then org, com
- **Braces**: K&R style (opening brace on same line)
- **Blank Lines**: One blank line between methods, two between class members

### Package Structure

```
src/main/java/com/example/demo/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── service/         # Business logic
├── repository/      # Data access (JPA)
├── model/           # Domain entities
├── dto/             # Data transfer objects
├── exception/       # Custom exceptions
├── security/        # Security configuration
└── util/            # Utility classes
```

### Error Handling

- Use `@ControllerAdvice` for global exception handling
- Return appropriate HTTP status codes (200, 201, 400, 404, 500)
- Use custom exception classes for business logic errors
- Include meaningful error messages in responses

### Controller Guidelines

- Use `@RestController` for REST endpoints
- Use `@RequestMapping` for path versioning
- Prefer `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Always validate request bodies with `@Valid`
- Use DTOs for request/response, not entities

### Service Guidelines

- Use interfaces for service definitions
- Prefer constructor injection with `@RequiredArgsConstructor` (Lombok)
- Keep services focused on business logic
- Handle transactions with `@Transactional`

### Repository Guidelines

- Extend `JpaRepository` or `CrudRepository`
- Define query methods following Spring Data JPA naming conventions
- Use `@Query` for complex queries
- Use `@Entity` for JPA entities

### Configuration

- Use `application.yaml` for configuration
- Use Spring profiles for environment-specific settings (`application-{profile}.yaml`)
- Never commit secrets; use environment variables or external configuration

### Testing

- Use JUnit 5 (Jupiter) with Spring Boot Test
- Use `@SpringBootTest` for integration tests
- Use `@WebMvcTest` for controller tests
- Use `@DataJpaTest` for repository tests
- Mock external dependencies with Mockito

### Documentation

- Use SpringDoc OpenAPI for API documentation
- Annotate controllers with `@Operation` and `@ApiResponse`
- Access Swagger UI at `/swagger-ui.html`
- Access OpenAPI docs at `/v3/api-docs`

## Common Dependencies

- Spring Boot Starter Web (REST)
- Spring Boot Starter Data JPA
- Spring Boot Starter Data Redis
- Spring Boot Starter Security
- Spring Boot Starter Validation
- SpringDoc OpenAPI
- Flyway (database migrations)
- Lombok (code generation)
- PostgreSQL driver

## Database

- Flyway is used for migrations (see `src/main/resources/db/migration`)
- Create migration files with naming convention: `V<version>__<description>.sql`
