# Items REST API

A simple Java Spring Boot backend that implements a RESTful API for managing a collection of items. Suitable for ecommerce (like Flipkart), movies (like Netflix), or any similar use case.

## Features

- **Item Model**: Items with `id`, `name`, `description`, `price`, `category`, `stockQuantity`, and `createdAt`
- **In-Memory Storage**: Uses `ArrayList` (thread-safe `CopyOnWriteArrayList`) for data persistence during runtime
- **REST Endpoints**:
  - Add items
  - Get item by ID
  - List all items
  - Search items by category and price range (ecommerce-style catalog browsing)
  - Update items (typical admin/catalog management)
  - Delete items
- **Input Validation**: Validates required fields (name, description) and constraints (including non-negative stock) using Bean Validation
- **Swagger UI**: Interactive API documentation at `/swagger-ui.html`

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**

## How to Run

### Option 1: Maven

```bash
cd items-api
mvn spring-boot:run
```

Or with the wrapper (if generated via `mvn -N wrapper:wrapper`):

```bash
./mvnw spring-boot:run
```

### Option 2: Build JAR and Run

```bash
mvn clean package
java -jar target/items-api-1.0.0.jar
```

The application starts on **http://localhost:8080**

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/items` | Add a new item |
| `GET` | `/api/items/{id}` | Get a single item by ID |
| `GET` | `/api/items` | Get all items or search with filters |
| `PUT` | `/api/items/{id}` | Update an existing item |
| `DELETE` | `/api/items/{id}` | Delete an item |

### Add Item (POST /api/items)

**Request body** (JSON):

```json
{
  "name": "Wireless Headphones",
  "description": "High-quality wireless headphones with noise cancellation",
  "price": 99.99,
  "category": "Electronics",
  "stockQuantity": 25
}
```

**Required fields**: `name`, `description` (must be non-blank, max 200 chars for name, max 1000 for description)

**Response** (201 Created):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Wireless Headphones",
  "description": "High-quality wireless headphones with noise cancellation",
  "price": 99.99,
  "category": "Electronics",
  "stockQuantity": 25,
  "createdAt": "2025-02-11T10:30:00Z"
}
```

### Get Item by ID (GET /api/items/{id})

Returns the item with the given ID, or `404 Not Found` if not found.

### Search / List Items (GET /api/items)

You can either get all items or filter using simple ecommerce-style search parameters:

```text
GET /api/items?category=Electronics&minPrice=50&maxPrice=200
```

All query parameters are optional:

- `category` – filter by category (case-insensitive exact match)
- `minPrice` – minimum price (inclusive)
- `maxPrice` – maximum price (inclusive)

### Update Item (PUT /api/items/{id})

Full update of an existing item (typical for ecommerce admin interfaces):

```json
{
  "name": "Wireless Headphones Pro",
  "description": "Updated description",
  "price": 129.99,
  "category": "Electronics",
  "stockQuantity": 50
}
```

Returns `200 OK` with the updated item, or `404 Not Found` if the ID does not exist.

### Delete Item (DELETE /api/items/{id})

Deletes an item by ID.

- Returns `204 No Content` on success
- Returns `404 Not Found` if the item does not exist

## Swagger Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

Use Swagger UI to explore and test the API interactively.

## Hosting / Deployment

**Note**: Vercel and Netlify are designed for frontend/serverless (Node.js, static sites) and have **limited or no support for traditional Java backends**. For a Java Spring Boot application, use one of these options:

| Platform | Best For | Notes |
|----------|----------|-------|
| [Render](https://render.com) | Free tier available | Supports Java, easy deploy from GitHub |
| [Railway](https://railway.app) | Quick deploys | Java support, Docker or buildpack |
| [Heroku](https://heroku.com) | Mature platform | Requires payment for most tiers |
| [Fly.io](https://fly.io) | Global deployment | Docker-based, supports JVM |
| [Google Cloud Run](https://cloud.google.com/run) | Serverless Java | Deploy as container |

### Deploy to Render (Example)

1. Push this project to a GitHub repository
2. Create a Render account and connect your repo
3. Create a new **Web Service**
4. Set **Build Command**: `mvn clean package -DskipTests`
5. Set **Start Command**: `java -jar target/items-api-1.0.0.jar`
6. Deploy

After deployment, update `OpenApiConfig.java` to add your deployed server URL to the Swagger config.

### Sending the Demo Link

Once deployed, send the base URL (e.g., `https://your-app.onrender.com`) to **dsvjavalinux@gmail.com**. The Swagger UI will be available at:

- `https://your-app.onrender.com/swagger-ui.html`

## Implementation Details

- **Validation**: Uses `@Valid` with Jakarta Bean Validation (`@NotBlank`, `@Size`) on `CreateItemRequest`
- **Error handling**: `GlobalExceptionHandler` returns structured 400 responses for validation failures
- **IDs**: Auto-generated UUIDs for new items
- **Thread safety**: `CopyOnWriteArrayList` for concurrent access to the in-memory store

## Project Structure

```
src/main/java/com/example/itemsapi/
├── ItemsApiApplication.java    # Main application
├── config/
│   └── OpenApiConfig.java      # Swagger configuration
├── controller/
│   └── ItemController.java     # REST endpoints
├── dto/
│   └── CreateItemRequest.java  # Request DTO with validation
├── exception/
│   └── GlobalExceptionHandler.java
├── model/
│   └── Item.java               # Item entity
├── repository/
│   └── ItemRepository.java     # In-memory store
└── service/
    └── ItemService.java        # Business logic
```

## License

MIT
