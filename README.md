# Stempy ERP

A small and evolving ERP backend built with Java.

This project is a personal attempt at building a structured, maintainable, and scalable business system from the ground up. The focus is on clean architecture, clear separation of concerns, and solid backend fundamentals rather than relying heavily on large frameworks.

It is currently under active development.

---

## Tech Stack

- **Java 21**
- **Maven**
- **Javalin** – lightweight HTTP framework
- **MySQL** – relational database
- **HikariCP** – connection pooling
- **JWT (jjwt)** – authentication
- **Jackson** – JSON serialization/deserialization
- **Redis (Lettuce)** – caching
- **dotenv-java** – environment configuration
- **SLF4J (simple)** – logging

---

## Project Goals

- Keep the architecture clean and explicit
- Separate HTTP, domain, and infrastructure layers
- Handle transactions carefully and consistently
- Make business logic reusable and testable
- Avoid unnecessary abstractions while staying scalable
- Learn and deeply understand how things work internally

This is not meant to be a "magic framework" project. Most behavior is implemented deliberately rather than auto-configured.

---

## Architecture Overview

The application is structured around:

### Modules

1. **stempy-domain** - Core domain entities and business logic
2. **stempy-application** - Application entry point and bootstrap logic
3. **stempy-infrastructure** - Database access and external service integration
4. **stempy-api** - HTTP API layer using Javalin
5. **stempy-service** - Business service implementations
6. **stempy-automation** - Background job scheduling and execution
7. **stempy-bootstrap** - Dependency injection container
8. **stempy-shared** - Shared utilities and common components

### Layer Responsibilities

#### Domain Layer (`stempy-domain`)

- Contains core business entities and value objects
- Defines business rules and invariants
- Independent of infrastructure concerns
- Includes the query system for safe dynamic filtering

#### Infrastructure Layer (`stempy-infrastructure`)

- Implements data access through repositories
- Handles database connections and transactions
- Integrates with external services (Redis, etc.)
- Contains SQL generation and database mapping logic

#### Application Layer (`stempy-application`)

- App entry point
- Contains bootstrap and initialization logic

#### Service Layer (`stempy-service`)

**facade:**

- Coordinates high-level business operations
- Manages transaction boundaries
- Composes services to fulfill use cases

**service:**

- Implements focused business operations
- Contains service-specific business logic
- Orchestrates domain entities and repositories

#### API Layer (`stempy-api`)

- Exposes RESTful endpoints
- Maps HTTP requests to application services
- Handles request/response transformation
- Implements middleware for cross-cutting concerns

#### Automation Layer (`stempy-automation`)

- Provides background job scheduling and execution
- Handles recurring and scheduled tasks
- Implements job queuing and worker threads

---

## Key Features

### Safe Dynamic Query System

One of the standout features of Stempy ERP is its sophisticated query system that allows clients to send complex filtering, sorting, and pagination instructions which are safely translated into SQL.

#### Components

1. **DomainQuery Model** - Database-agnostic representation of queries
2. **Filter Tree Structure** - Enables complex nested filtering with AND/OR logic
3. **SQL Translation Layer** - Converts domain queries to safe SQL with parameter binding
4. **Field Validation** - Prevents SQL injection by validating field names against predefined mappings

#### Security Features

- All SQL parameters are properly bound (prevents injection)
- Column names are validated against field maps
- Complex nested filters are supported without manual string concatenation
- Field-level access control prevents querying unauthorized fields

### Automation Framework

The automation module provides a lightweight job scheduling and execution framework:

- Interval-based and fixed-time job scheduling
- Thread-safe job queue for execution decoupling
- Background worker threads for job processing
- Dynamic job schedule refreshing
- Dependency injection support for job implementations

---

## Project Structure

```
stempy-erp/
├── docs/                   # Documentation
│   ├── automation.md       # Automation system documentation
│   ├── query.md            # Query system documentation
│   └── tables/             # Database schema documentation
├── stempy-api/             # HTTP API layer
├── stempy-application/     # Application entry point
├── stempy-automation/      # Background job system
├── stempy-bootstrap/       # Dependency injection container
├── stempy-domain/          # Core domain logic
├── stempy-infrastructure/  # Database and external services
├── stempy-service/         # Business services
├── stempy-shared/          # Shared utilities
├── docker-compose.yml      # Redis container configuration
├── pom.xml                 # Maven parent configuration
└── .env.example            # Environment configuration template
```

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- MySQL database
- Redis server (can use docker-compose)

### Setup

1. Clone the repository
2. Copy `.env.example` to `.env` and configure your settings
3. Set up MySQL database
4. Start Redis: `docker-compose up -d`
5. Build the project: `mvn clean install`

### Running the Application

```bash
mvn exec:java -pl stempy-application
```

The application will start on the port specified in your `.env` file (default: 7070).

### Running Tests

```bash
mvn test
```

---

## Query System Deep Dive

The query system is designed to solve common problems with dynamic filtering in enterprise applications:

### Problems Addressed

1. **Dynamic Filtering Without Manual SQL Construction**
   - Eliminates error-prone string concatenation
   - Supports complex nested conditions
   - Maintains SQL injection protection

2. **Separation Between HTTP and Persistence Layers**
   - Clients send JSON query definitions
   - Repositories work with domain query objects
   - Clean separation of concerns

3. **Safe SQL Generation**
   - Automatic parameter binding
   - Field name validation
   - Type-safe parameter handling

### Core Components

#### DomainQuery

Represents a query independent of HTTP or SQL:

```java
public record DomainQuery(
    FilterNode filters,
    List<SortSpec> sortSpec,
    PageSpec pageSpec
)
```

#### Filter System

Filters are represented as a tree structure:

- **FilterCondition** - Single field comparison
- **FilterGroup** - Group of filters with logical operators (AND/OR)

#### SQL Translation Process

```
HTTP request
     ↓
RequestQueryMapper
     ↓
DomainQuery
     ↓
DomainQuerySQLTranslator
     ↓
SQLBuilder
     ↓
Parameterized SQL
```

### Example Usage

Client sends JSON:

```json
{
  "query": {
    "filters": {
      "operator": "AND",
      "children": [
        {
          "field": "enabled",
          "comparison": "EQUALS",
          "value": true
        },
        {
          "operator": "OR",
          "children": [
            {
              "field": "description",
              "comparison": "LIKE",
              "value": "product"
            }
          ]
        }
      ]
    },
    "sorting": [
      {
        "field": "createdAt",
        "direction": "DESC"
      }
    ],
    "pagination": {
      "page": 1,
      "size": 50
    }
  }
}
```

This safely translates to SQL:

```sql
SELECT * FROM products
WHERE (
  products.enabled = ?
  AND (
    products.description LIKE ?
  )
)
ORDER BY products.created_at DESC
LIMIT 50
OFFSET 50
```

---

## Automation System

The automation module provides robust background job processing:

### Architecture

```
AutomationManager (Singleton)
├── JobQueue (Thread-safe queue)
├── WorkerThread (Background executor)
├── Scheduler (Job planner)
└── JobFactory (DI for job creation)
```

### Features

- Interval scheduling with millisecond precision
- Run-times scheduling for specific daily execution times
- Thread-safe job queue decoupling scheduling from execution
- Dynamic schedule refresh without application restart
- Dependency injection support for job implementations

---

## Contributing

This project is still evolving. The structure, conventions, and internal patterns may change as the system grows.
