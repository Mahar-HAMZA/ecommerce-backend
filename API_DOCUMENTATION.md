# REST API Documentation

## 1. Overview

Base URL for local development is expected to be:

`http://localhost:8080`

Confirm the active server port and context path in the current
application configuration.

**Important:** The endpoints below are based on controller mappings and
security rules observed during development. Verify DTO fields and
response schemas against the current source code before using this as a
final external API contract.

## 2. Authentication and access

The current security configuration permits: - `POST /users/register` -
`POST /users/login` - `GET /api/products/**` - `GET /api/categories/**`

It requires `ADMIN` for: - `POST /api/categories` -
`PUT /api/categories/**` - `DELETE /api/categories/**` -
`POST /api/products` - `PUT /api/products/**` -
`DELETE /api/products/**` - `PUT /api/orders/**`

Other routes require authentication. The order service additionally
checks ownership for single-order retrieval and returns all orders only
to administrators.

Use the authentication mechanism implemented by the current project.
Confirm whether login returns a JWT and how the token must be sent
before publishing token-specific instructions.

## 3. Users

  Method   Path                Purpose               Access
  -------- ------------------- --------------------- --------
  POST     `/users/register`   Register a user       Public
  POST     `/users/login`      Authenticate a user   Public

Request and response fields depend on `UserCreateDTO`, `LoginDTO`, and
the corresponding response DTOs. Check these classes for the exact JSON
schema.

## 4. Products

### List, search, filter, sort, and paginate products

`GET /api/products`

Query parameters:

  -----------------------------------------------------------------------
  Parameter         Type              Default           Description
  ----------------- ----------------- ----------------- -----------------
  `pageNumber`      integer           `0`               Zero-based page
                                                        index

  `pageSize`        integer           `10`              Number of results
                                                        per page; service
                                                        validates allowed
                                                        bounds

  `sortBy`          string            `price`           Sort field;
                                                        allowed fields
                                                        observed:
                                                        `price`,
                                                        `productName`,
                                                        `createdAt`, `id`

  `direction`       string            `DESC`            Sort direction:
                                                        `ASC` or `DESC`

  `search`          string            none              Optional
                                                        product-name
                                                        search

  `minPrice`        decimal           none              Optional minimum
                                                        price

  `maxPrice`        decimal           none              Optional maximum
                                                        price

  `categoryId`      integer/long      none              Optional category
                                                        filter
  -----------------------------------------------------------------------

Example:

``` http
GET /api/products?pageNumber=0&pageSize=10&sortBy=price&direction=DESC
```

Search example:

``` http
GET /api/products?search=phone
```

Price range example:

``` http
GET /api/products?minPrice=100&maxPrice=500
```

Category filter example:

``` http
GET /api/products?categoryId=2
```

Invalid query conversion example:

``` http
GET /api/products?pageNumber=abc
```

Expected status: `400 Bad Request`.

The response uses Spring Data's `Page` structure mapped to `ProductDTO`.
Confirm the exact JSON fields from the current DTO and runtime response.

### Product write routes

  Method   Path                   Purpose          Access
  -------- ---------------------- ---------------- ---------
  POST     `/api/products`        Create product   `ADMIN`
  PUT      `/api/products/{id}`   Update product   `ADMIN`
  DELETE   `/api/products/{id}`   Delete product   `ADMIN`

Use `ProductCreateDTO` and the actual update DTO/schema in source code
to build request bodies. These schemas are intentionally not guessed
here.

## 5. Categories

  ------------------------------------------------------------------------------
  Method            Path                     Purpose           Access
  ----------------- ------------------------ ----------------- -----------------
  GET               `/api/categories`        List categories   Public

  GET               `/api/categories/{id}`   Retrieve category Public
                                             by ID, if mapped  

  POST              `/api/categories`        Create category   `ADMIN`

  PUT               `/api/categories/{id}`   Update category   `ADMIN`

  DELETE            `/api/categories/{id}`   Delete category   `ADMIN`
  ------------------------------------------------------------------------------

Verify exact mappings in `CategoryController.java`; the read routes
shown with `{id}` are conditional on the actual controller mapping.

## 6. Orders

### Create an order

`POST /api/orders`

Access: authenticated user.

The request body is based on `OrderCreateDTO` and contains a non-empty
`items` array. Each item has: - `productId` --- required - `quantity`
--- required and greater than zero

Example request:

``` json
{
  "items": [
    {
      "productId": 5,
      "quantity": 2
    }
  ]
}
```

The service calculates item subtotals and total amount, checks stock,
reduces stock, associates the order with the logged-in user, and saves
the order. The controller returns `201 Created` on success.

### Retrieve one order

`GET /api/orders/{id}`

Access: authenticated user. A customer may retrieve their own order; an
administrator may retrieve any order.

### List orders

`GET /api/orders`

Access: authenticated user. Customers receive their own orders;
administrators receive all orders. The service orders the list by
creation time descending.

### Update order status

`PUT /api/orders/{id}`

Access: `ADMIN`.

The request body uses `OrderStatusUpdateDTO`. Check that DTO and the
`OrderStatus` enum for the allowed JSON values.

## 7. Error responses

The project uses `CategoryExceptionResponse` as the shared error
response type in the global exception handler. Fields observed
include: - `statusCode` - `message`

Examples of intended statuses: - `400 Bad Request` --- invalid input,
validation failure, malformed request body, or query-parameter type
mismatch. - `401 Unauthorized` --- unauthenticated or invalid login,
depending on security handling. - `403 Forbidden` --- authenticated user
lacks permission or order ownership. - `404 Not Found` --- requested
resource does not exist. - `500 Internal Server Error` --- unexpected
server error, with a generic client-facing message.

Exact response bodies may vary by exception handler. Verify against
current runtime responses.

## 8. Related documents

-   [Requirements](REQUIREMENTS.md)
-   [Database design](DATABASE_DESIGN.md)
-   [Architecture](ARCHITECTURE.md)
-   [Testing](TESTING.md)
