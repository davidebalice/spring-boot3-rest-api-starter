# Spring Boot 3 REST API Starter

A Spring Boot 3 REST API template for quickly prototyping new projects.
It includes JWT authentication and CRUD APIs for products, categories, subcategories, attributes, customers, users and product image galleries, with OpenAPI documentation. It is used as the backend of an Angular products management panel.

**Live demo (Angular panel):** <https://angular-products.davidebalice.dev>

![Screenshot](https://www.aroundweb.it/screenshot/angular-panel1.jpg)

![Screenshot](https://www.aroundweb.it/screenshot/angular-panel2.jpg)

The frontend is available in a separate repository: [angular-products-admin-panel](https://github.com/davidebalice/angular-products-admin-panel).

## Features

- JWT authentication with Spring Security and CSRF token endpoint
- CRUD APIs for products, categories, subcategories, attributes, attribute values, customers and users
- Product search, search by category and lookup by SKU
- Product attributes and values assignment
- Product image upload and image gallery
- DTOs mapped with ModelMapper
- OpenAPI / Swagger UI documentation
- Spring Boot Actuator endpoints
- Demo mode that blocks write operations
- Sample MySQL database in `sql/db.sql`
- Dockerfile to run the packaged JAR

## Tech stack

- [Java 17](https://openjdk.org/)
- [Spring Boot 3](https://spring.io/projects/spring-boot) (Web, Data JPA, Security, Validation, Actuator)
- [MySQL](https://www.mysql.com/) / [MariaDB](https://mariadb.org/)
- [JJWT](https://github.com/jwtk/jjwt)
- [springdoc-openapi](https://springdoc.org/) (Swagger UI)
- [ModelMapper](https://modelmapper.org/)
- [Maven](https://maven.apache.org/)

## Getting started

### Requirements

- Java 17 or later
- MySQL or MariaDB

### Installation

```bash
git clone https://github.com/davidebalice/spring-boot3-rest-api-starter.git
cd spring-boot3-rest-api-starter
```

Create a database and import the sample data:

```bash
mysql -u root -p -e "CREATE DATABASE jdbc1"
mysql -u root -p jdbc1 < sql/db.sql
```

### Configuration

Settings are in `src/main/resources/application.properties`:

| Property                                  | Description                                                            |
| ----------------------------------------- | ---------------------------------------------------------------------- |
| `spring.datasource.url`                   | JDBC URL of the database (default `jdbc:mysql://localhost:3306/jdbc1`) |
| `spring.datasource.username` / `password` | Database credentials                                                   |
| `server.port`                             | HTTP port (default `8081`)                                             |
| `upload.path`                             | Absolute folder where uploaded images are saved                        |
| `security.jwt.secret-key`                 | Secret key used to sign JWTs (change it)                               |
| `security.jwt.expiration-time`            | Token lifetime in milliseconds                                         |
| `demo.mode.enabled`                       | `true` to block write operations                                       |

### Run

| Command                  | Description                              |
| ------------------------ | ---------------------------------------- |
| `./mvnw spring-boot:run` | Start the API on `http://localhost:8081` |
| `./mvnw clean package`   | Build the JAR in `target/`               |
| `./mvnw test`            | Run the tests                            |

### Docker

```bash
./mvnw clean package -DskipTests
docker build -t restapi .
docker run -p 8081:8081 restapi
```

### API documentation

Swagger UI is available at <http://localhost:8081/swagger-ui/index.html>.

## API endpoints

Get a token with `POST /api/v1/login`, then send it in the `Authorization: Bearer <token>` header.

### Auth

| Method | Endpoint               | Description                |
| ------ | ---------------------- | -------------------------- |
| POST   | `/api/v1/login`        | Log in and get a JWT       |
| POST   | `/api/v1/authenticate` | Authenticate and get a JWT |
| GET    | `/api/v1/validate`     | Validate a token           |
| POST   | `/api/v1/csrf`         | Get a CSRF token           |

### Products

| Method | Endpoint                                        | Description                                |
| ------ | ----------------------------------------------- | ------------------------------------------ |
| GET    | `/api/v1/products`                              | List products                              |
| GET    | `/api/v1/products/{id}`                         | Get a product                              |
| GET    | `/api/v1/products/product`                      | Get a product by query string id           |
| GET    | `/api/v1/products/sku/{sku}`                    | Get a product by SKU                       |
| GET    | `/api/v1/products/search`                       | Search products                            |
| GET    | `/api/v1/products/searchByCategoryId`           | Search products by category                |
| GET    | `/api/v1/products/stream-test`                  | List all products (stream mapping example) |
| POST   | `/api/v1/products/add`                          | Create a product                           |
| PATCH  | `/api/v1/products/{id}`                         | Update a product                           |
| DELETE | `/api/v1/products/{id}`                         | Delete a product                           |
| POST   | `/api/v1/products/{id}/uploadImage`             | Upload the product image                   |
| GET    | `/api/v1/products/image/{fileName}`             | Get a product image                        |
| POST   | `/api/v1/products/{id}/attributes`              | Add attributes to a product                |
| POST   | `/api/v1/products/{id}/remove-attributes`       | Remove attributes from a product           |
| GET    | `/api/v1/products/{id}/setted-attributes-value` | Get the attributes and values of a product |
| GET    | `/api/v1/products/{productId}/gallery`          | Get the product gallery                    |
| POST   | `/api/v1/products/{productId}/gallery/upload`   | Upload gallery images                      |
| GET    | `/api/v1/products/gallery/{fileName}`           | Get a gallery image                        |

### Categories and subcategories

| Method | Endpoint                              | Description                      |
| ------ | ------------------------------------- | -------------------------------- |
| GET    | `/api/v1/categories`                  | List categories                  |
| GET    | `/api/v1/categories/search`           | Search categories                |
| GET    | `/api/v1/categories/{id}`             | Get a category                   |
| POST   | `/api/v1/categories/add`              | Create a category                |
| PATCH  | `/api/v1/categories/{id}`             | Update a category                |
| DELETE | `/api/v1/categories/{id}`             | Delete a category                |
| GET    | `/api/v1/subcategories/idcat/{idcat}` | List subcategories of a category |
| GET    | `/api/v1/subcategories/{id}`          | Get a subcategory                |
| POST   | `/api/v1/subcategories/add`           | Create a subcategory             |
| PATCH  | `/api/v1/subcategories/{id}`          | Update a subcategory             |
| DELETE | `/api/v1/subcategories/{id}`          | Delete a subcategory             |

### Attributes and values

| Method | Endpoint                               | Description                       |
| ------ | -------------------------------------- | --------------------------------- |
| GET    | `/api/v1/attributes`                   | List attributes                   |
| GET    | `/api/v1/attributes/attributes-values` | List attributes with their values |
| GET    | `/api/v1/attributes/{id}`              | Get an attribute                  |
| POST   | `/api/v1/attributes/add`               | Create an attribute               |
| PATCH  | `/api/v1/attributes/{id}`              | Update an attribute               |
| DELETE | `/api/v1/attributes/{id}`              | Delete an attribute               |
| GET    | `/api/v1/values/idattr/{idattr}`       | List values of an attribute       |
| GET    | `/api/v1/values/{id}`                  | Get a value                       |
| POST   | `/api/v1/values/add`                   | Create a value                    |
| PATCH  | `/api/v1/values/{id}`                  | Update a value                    |
| DELETE | `/api/v1/values/{id}`                  | Delete a value                    |

### Customers and users

| Method | Endpoint                   | Description         |
| ------ | -------------------------- | ------------------- |
| GET    | `/api/v1/customers`        | List customers      |
| GET    | `/api/v1/customers/search` | Search customers    |
| GET    | `/api/v1/customers/{id}`   | Get a customer      |
| POST   | `/api/v1/customers/add`    | Create a customer   |
| PATCH  | `/api/v1/customers/{id}`   | Update a customer   |
| DELETE | `/api/v1/customers/{id}`   | Delete a customer   |
| GET    | `/api/v1/users/me`         | Get the logged user |
| GET    | `/api/v1/users`            | List users          |
| GET    | `/api/v1/users/{id}`       | Get a user          |
| POST   | `/api/v1/users/add`        | Create a user       |
| PATCH  | `/api/v1/users/{id}`       | Update a user       |
| DELETE | `/api/v1/users/{id}`       | Delete a user       |

## Project structure

```text
src/main/java/com/restapi/
├── config/       # Application class, security, demo mode, ModelMapper
├── controller/   # REST controllers
├── dto/          # Data transfer objects
├── mapper/       # Entity/DTO mappers
├── model/        # JPA entities
├── repository/   # Spring Data repositories
├── security/     # JWT service and filter
└── service/      # Business logic
sql/db.sql        # Sample database
```

## Author

**Davide Balice** – [davidebalice.dev](https://www.davidebalice.dev)
