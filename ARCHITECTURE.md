# Software Architecture Specification

## 1. Overview

The E-Commerce REST API Backend Service is developed using the Java Spring Boot framework. It follows a clean, layered architectural pattern with a package-by-feature organization under the root package `com.hamza.ecommerce_backend`.

**Core Request Flow:**

`HTTP Client → Controller Layer → Service Layer → Repository Layer → MySQL Database`

Responses are mapped back through the layers in reverse order.

## 2. Architectural Layers

### 2.1 Controller Layer (`@RestController`)
Responsible for exposing the API contract via REST endpoints. Its primary functions include:
*   Mapping HTTP methods to service operations.
*   Binding path variables, query parameters, and request bodies.
*   Triggering incoming request validation using the Jakarta Validation API (`@Valid`).
*   Invoking business service logic.
*   Returning appropriate HTTP response status codes and serialized data.

*Examples: `product/controller/ProductController.java`, `order/controller/OrderController.java`.*

### 2.2 Service Layer (`@Service`)
Encapsulates all business logic and orchestrates domain operations. Responsibilities include:
*   Enforcing domain-specific rules.
*   Coordinating transactional boundaries across multiple repository calls.
*   Performing calculations (e.g., order totals).
*   Implementing inventory management logic (e.g., stock check and reduction during order placement).
*   Enforcing data access ownership (authorization).

*Example: `order/service/OrderService.java`.*

### 2.3 Repository Layer (`@Repository`)
Facilitates persistence operations, leveraging Spring Data JPA for abstraction over Hibernate ORM. It provides:
*   Standard CRUD operations via `JpaRepository` extension.
*   Derived query methods.
*   Explicit entity existence checks.

*Examples: `ProductRepository`, `OrderRepository`, `UserRepository`, `CategoryRepository`.*

### 2.4 DTO and Mapper Layer
DTOs (Data Transfer Objects) are distinct from persistence entities, defining the precise shape of API requests and responses. Mappers (e.g., `OrderMapper`) convert between DTOs and entities, ensuring decoupling between the API contract and the database schema.

### 2.5 Exception Handling Layer (`@RestControllerAdvice`)
A global exception handler centralizes error mapping. It intercepts exceptions from all layers and converts them into standardized JSON error responses, covering validation errors, domain-specific exceptions, authentication/authorization failures, and unexpected system errors.

## 3. Feature Modules

The project is structured around the following feature packages:

*   `category`: Category entity, repository, and REST routes.
*   `product`: Product entities, service logic, DTOs, and repositories.
*   `order`: Order controller, service, entities (Order, OrderItem), DTOs, mappers, repositories, and domain exceptions.
*   `user`: User model, repository, registration/login logic, and security integration.
*   `exception`: Global exception handling configurations.

> **Pending Verification:** The exact structure, endpoints, and integration status of a discussed Cart module must be confirmed before addition to this documentation.

## 4. Security Model

Security is implemented via Spring Security and enforces the following configuration:
*   **Permitted (Public) Routes:** User registration (`POST /users/register`), login (`POST /users/login`), product and category retrieval (`GET /api/products/**`, `GET /api/categories/**`).
*   **Authenticated Operations:** Most non-public routes require a valid authenticated identity.
*   **ADMIN-Restricted Operations:** Product/Category write operations (Create, Update, Delete) and order status updates (`PUT /api/orders/**`) require the `ADMIN` role.

The Service Layer additionally enforces ownership authorization for sensitive operations (e.g., a customer can only retrieve their own orders).

*(Verification: Cross-check the current security configuration and user details service for definitive token handling mechanisms.)*

## 5. Order Creation Transactional Workflow

Based on the `OrderService.makeOrder()` implementation:

*(Operations 1-7 are executed within a single `@Transactional` boundary)*

1.  Initialize an empty `Order` entity, item list, and total amount.
2.  Retrieve the authenticated user's identity from `SecurityContextHolder`.
3.  Iterate through the request DTO items:
    *   Retrieve the corresponding `Product` entity (failure terminates).
    *   Verify sufficient stock availability.
    *   Instantiate an `OrderItem` entity.
    *   Calculate and store unit price and subtotal.
    *   Increment the grand total.
    *   **Decrement** product stock quantity.
4.  Persist the updated product entities (stock reduction).
5.  Associate the authenticated `User` entity with the `Order`.
6.  Assign the items and final total to the `Order`.
7.  Persist the `Order` entity and map the result to an `OrderDTO`.

> **Verification Task:** Concurrency handling and transactional rollback behavior must be validated to ensure inventory consistency.

## 6. Validation and Error Handling Principles

*   DTOs employ Jakarta Bean Validation annotations (e.g., `@NotNull`, `@Positive`, `@NotEmpty`, `@Valid`).
*   Query parameters are validated via specific service logic (page bounds, pricing).
*   Input mismatches (e.g., `pageNumber=abc`) are correctly trapped and return `400 Bad Request`.

## 7. Architectural Checklist

*   [ ] Verify package structure against the active repository.
*   [ ] Review entity-to-DTO mappings for data leakage.
*   [ ] Confirm transaction boundaries for order creation and inventory reduction.
*   [ ] Test role restrictions (`ADMIN` vs `CUSTOMER`) and data ownership logic.
*   [ ] Confirm final authentication mechanism (JWT) and token handling.
*   [ ] Generate and attach a complete system architecture diagram.