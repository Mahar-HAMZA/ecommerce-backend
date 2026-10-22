# Software Requirements Specification

## 1. Document purpose

This document describes the goals, functional requirements, access
rules, and quality expectations for the E-Commerce REST API Backend
Service.

**Status convention** - **Implemented / observed:** supported by the
code or development checks described so far. - **To verify:** requires
confirmation against the current source code and configuration. -
**Planned:** a future improvement, not a claim of current functionality.

## 2. Project overview

The system is a backend REST API for an e-commerce application. It
manages users, product categories, products, and orders, with
persistence through Spring Data JPA/Hibernate and MySQL.

The project uses a layered architecture and package-by-feature
organization under `com.hamza.ecommerce_backend`.

## 3. User roles

### Customer (`CUSTOMER`)

Expected responsibilities: - Register and log in. - Browse products and
categories. - Access their own order information. - Create orders when
authenticated.

The current order service checks the logged-in user's identity when
retrieving a single order and returns only that customer's orders from
the order-list endpoint.

### Administrator (`ADMIN`)

Expected responsibilities: - Create, update, and delete products. -
Create, update, and delete categories. - View all orders. - Update order
status.

The current Spring Security configuration restricts the documented
product/category write routes and order status update route to `ADMIN`.

## 4. Functional requirements

  -----------------------------------------------------------------------
  ID                      Requirement             Current status
  ----------------------- ----------------------- -----------------------
  FR-01                   Allow a new user to     Implemented endpoint
                          register.               observed; regression
                                                  coverage to verify.

  FR-02                   Allow a registered user Implemented endpoint
                          to log in.              observed; token/session
                                                  behaviour to verify
                                                  against current code.

  FR-03                   Allow public product    Implemented and tested.
                          browsing.               

  FR-04                   Support product         Implemented and tested.
                          pagination.             

  FR-05                   Support product sorting Implemented and tested.
                          by approved fields and  
                          direction.              

  FR-06                   Search products by      Implemented and tested.
                          name.                   

  FR-07                   Filter products by      Implemented and tested.
                          minimum/maximum price.  

  FR-08                   Filter products by      Implemented; test
                          category ID.            coverage should be kept
                                                  in regression suite.

  FR-09                   Allow admins to create, Routes and role
                          update, and delete      restrictions observed;
                          products.               full regression status
                                                  to verify.

  FR-10                   Allow public category   Route restriction
                          browsing.               observed; full
                                                  regression status to
                                                  verify.

  FR-11                   Allow admins to create, Routes and role
                          update, and delete      restrictions observed;
                          categories.             full regression status
                                                  to verify.

  FR-12                   Create an order         Implemented; edge-case
                          containing one or more  regression testing
                          product items.          ongoing.

  FR-13                   Calculate each order    Implemented in order
                          item's subtotal and the service.
                          overall total.          

  FR-14                   Check product           Implemented;
                          availability and reduce concurrency and
                          stock during order      duplicate-item edge
                          creation.               cases require review.

  FR-15                   Allow customers to      Implemented in order
                          retrieve only their own service.
                          orders.                 

  FR-16                   Allow admins to         Implemented in order
                          retrieve all orders.    service.

  FR-17                   Restrict order status   Security rule observed;
                          updates to admins.      test with both roles.

  FR-18                   Return consistent API   Global exception
                          errors for invalid      handler exists; some
                          requests.               cases tested.

  FR-19                   Support cart operations Verify current
                          and cart-to-order       implementation and
                          integration.            tested endpoints.

  FR-20                   Support a payment       Planned / verify; do
                          workflow.               not claim complete
                                                  without implementation
                                                  and tests.
  -----------------------------------------------------------------------

## 5. Validation requirements

-   Required fields must be present.
-   Product quantity in an order item must be greater than zero.
-   An order request must contain at least one item.
-   Product IDs must refer to existing products.
-   Requested quantities must not exceed available stock.
-   Product-list pagination values must be valid.
-   Page size must stay within the service's configured allowed range.
-   Sort direction and sort field must be accepted values.
-   If both price limits are supplied, the minimum must not exceed the
    maximum.
-   Request-body and query-parameter conversion errors should return
    suitable `4xx` responses rather than unexpected `500` responses.

## 6. Security requirements

-   Public access should be limited to explicitly public routes.
-   Administrative write operations must require the `ADMIN` role.
-   Authenticated customers must not read another customer's order.
-   Secrets and database credentials must not be committed to source
    control.
-   Authentication mechanism and token lifecycle must be documented only
    after verification against the current implementation.
-   Authorization must be tested at the HTTP layer and, where
    appropriate, enforced in business logic.

## 7. Non-functional requirements

### Maintainability

-   Keep responsibilities separated across controller, service, and
    repository layers.
-   Prefer feature-based packages.
-   Use DTOs for API request/response models where appropriate.
-   Keep exception responses consistent.

### Reliability and data consistency

-   An order should not be partially created if a transactional
    operation fails.
-   Stock updates and order persistence should remain consistent.
-   Invalid input should fail with clear client-facing responses.

### Usability for API consumers

-   Use appropriate HTTP status codes.
-   Provide clear validation and error messages.
-   Document required authentication and request formats.

### Testability

-   Provide repeatable Postman requests.
-   Test successful and unsuccessful cases.
-   Re-run relevant regression tests after service, validation, or
    security changes.

## 8. Constraints and assumptions

-   The backend is a REST API; a separate frontend is not assumed by
    this document.
-   MySQL is the intended relational database.
-   Exact dependency versions must be read from `pom.xml`.
-   Exact entity fields and relationships must be confirmed against the
    current entity classes.
-   Payment gateway, email notifications, deployment, Docker, and
    production observability are not claimed as implemented here.

## 9. Acceptance criteria

A feature is ready to be marked complete when: 1. Its implementation is
reviewed. 2. Expected success cases pass. 3. Relevant validation,
authorization, and not-found cases pass. 4. Regression tests for
affected functionality pass. 5. The API documentation is updated. 6. The
change is committed with a clear, meaningful Git message.
