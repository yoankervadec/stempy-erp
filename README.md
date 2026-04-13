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

The application follows a layered architecture with clear dependency direction:

```
┌─────────────────────────────────────────────────────────────────┐
│                      stempy-api                                 │
│              (HTTP layer, controllers, routes)                  │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                   stempy-application                           │
│                  (Entry point, bootstrap)                       │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                     stempy-service                             │
│         (Business logic, service interfaces & impls)            │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                 stempy-infrastructure                          │
│      (Database access, repositories, external services)        │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                      stempy-domain                              │
│         (Core entities, value objects, business rules)          │
└─────────────────────────────────────────────────────────────────┘
```

---

## Modules

### stempy-domain

Core domain layer - contains business entities, value objects, and domain rules. No dependencies on infrastructure.

**Package Structure:**

```
com.lesconstructionssapete.stempyerp.domain
├── auth/                  # Authentication domain objects
│   ├── ApplicationPermission
│   ├── ApplicationPermissionSet
│   ├── ApplicationRole
│   ├── AuthToken
│   └── UserCredential
├── automation/            # Job scheduling domain
│   ├── Job
│   └── JobLog
├── common/                # Shared value objects
│   ├── Address
│   └── Contact
├── constant/              # Reference/constant entities
│   ├── ConstantEntity (interface)
│   ├── DomainEntityType
│   ├── PaymentMethod
│   ├── RetailCategory
│   ├── TaxGroup
│   ├── TaxGroupLine
│   └── TaxRate
├── exception/             # Domain-specific exceptions
│   ├── ConcurrencyConflictException
│   ├── FieldNotFoundException
│   ├── SequenceNotFoundException
│   ├── ValidationException
│   └── ...
├── field/                 # Field definitions for filtering/sorting
│   ├── DomainField (interface)
│   ├── DomainFieldResolver (interface)
│   ├── DefaultDomainFieldResolver
│   ├── auth/              # Auth field enums
│   ├── automation/        # Automation field enums
│   ├── retailproduct/     # Retail product field enums
│   ├── sequence/          # Sequence field enums
│   └── user/             # User field enums
├── generic/               # Base entity class
│   └── GenericEntity
├── query/                 # Query/filter/sort model
│   ├── DomainQuery
│   ├── FilterNode
│   ├── FilterCondition
│   ├── FilterGroup
│   ├── SortSpec
│   ├── PageSpec
│   ├── ComparisonOperator
│   ├── LogicalOperator
│   └── builder/
│       ├── DomainQueryBuilder
│       └── FilterBuilder
├── repository/            # Repository interfaces
│   ├── SequenceRepository
│   ├── AutomationRepository
│   ├── ConstantRepository
│   ├── UserRepository
│   └── auth/
│       └── UserCredentialRepository
├── retailproduct/         # Retail product entities
│   ├── RetailProduct
│   ├── RetailProductMaster
│   ├── RetailProductBarcode
│   ├── RetailProductCost
│   ├── RetailProductPrice
│   └── RetailProductMasterPolicy
├── sequence/              # Sequence domain object
│   └── LiveSequence
└── user/                  # User domain object
    └── User
```

### stempy-service

Service layer - contains business logic, service interfaces, and implementations.

**Package Structure:**

```
com.lesconstructionssapete.stempyerp
├── port/                  # Port interfaces (SPI)
│   ├── cache/
│   │   ├── CacheProvider
│   │   └── ConstantCache
│   ├── persistence/
│   │   └── SQLConnectionProvider
│   ├── security/
│   │   ├── PasswordHashProvider
│   │   └── TokenProvider
│   └── transaction/
│       ├── TransactionRunner
│       ├── TransactionCallback
│       └── TransactionPropagation
└── service/
    ├── spi/               # Service interfaces
    │   ├── authentication/
    │   │   └── AuthService
    │   ├── authorization/
    │   │   └── AuthorizationService
    │   ├── constant/
    │   │   └── ConstantService
    │   ├── retailproduct/
    │   │   └── RetailProductService
    │   ├── sequence/
    │   │   └── SequenceService
    │   └── user/
    │       └── UserService
    ├── impl/              # Service implementations
    │   ├── authentication/
    │   │   └── AuthServiceImpl
    │   ├── authorization/
    │   │   ├── AuthorizationServiceImpl
    │   │   ├── PermissionRegistry
    │   │   ├── RolePermissionService
    │   │   └── UserPermissionService
    │   ├── constant/
    │   │   └── ConstantServiceImpl
    │   ├── retailproduct/
    │   │   └── RetailProductServiceImpl
    │   ├── sequence/
    │   │   └── SequenceServiceImpl
    │   └── user/
    │       └── UserServiceImpl
    └── numbering/         # Entity ID/number generation
        ├── EntityNumberGenerator (interface)
        ├── EntityNumberGeneratorRegistry (interface)
        ├── DefaultEntityNumberGeneratorRegistry
        ├── PaddedPrefixGenerator
        └── RetailProductNumberGenerator
```

### stempy-infrastructure

Infrastructure layer - implements persistence, external integrations, and infrastructure concerns.

**Package Structure:**

```
com.lesconstructionssapete.stempyerp.infrastructure
├── exception/             # Infrastructure exceptions
│   ├── DatabaseAccessException
│   ├── DuplicateKeyException
│   ├── ForeignKeyViolationException
│   ├── NotNullConstraintException
│   ├── SequenceUpdateException
│   └── TransactionFailureException
├── field/                 # SQL field mappings
│   ├── SQLField (base class)
│   ├── authentication/
│   ├── authorization/
│   ├── automation/
│   ├── retailproduct/
│   ├── sequence/
│   └── user/
├── mapper/                # Row/SQL mappers
│   ├── SQLInstantMapper
│   ├── authentication/
│   ├── authorization/
│   ├── automation/
│   ├── retailproduct/
│   └── user/
├── persistence/           # Core persistence
│   ├── SQLExecutor
│   ├── TransactionManager
│   ├── HikariConnectionProvider
│   └── repository/
│       ├── authentication/
│       ├── authorization/
│       ├── automation/
│       ├── constant/
│       ├── retailproduct/
│       ├── sequence/
│       └── user/
├── query/                 # Query translation
│   ├── DomainQuerySQLTranslator
│   ├── QueryCache
│   ├── SQLBinder
│   ├── SQLBuilder
│   └── Query (enum)
├── redis/                 # Redis infrastructure
│   ├── RedisProvider
│   ├── LettuceRedisCache
│   └── constant/
│       └── RedisConstantCache
└── security/              # Security implementations
    ├── JwtTokenProvider
    └── PBKDF2PasswordProvider
```

### stempy-api

HTTP API layer using Javalin - exposes REST endpoints.

### stempy-application

Application entry point and bootstrap logic.

### stempy-automation

Background job scheduling and execution framework.

### stempy-bootstrap

Dependency injection container and module wiring.

### stempy-shared

Shared utilities and common components.

---

## Key Design Patterns

### Domain Field Pattern

Each domain entity has a corresponding `*Field` enum implementing `DomainField`:

```java
public enum RetailProductField implements DomainField {
    ENTITY_ID("entityId"),
    NAME("name"),
    DESCRIPTION("description"),
    // ...
    ;

    @Override
    public String logicalName() {
        return "RetailProduct." + this.fieldName;
    }
}
```

These are used for:

- Safe filtering in queries
- Sorting specifications
- Field validation

### SQL Field Mapping

Infrastructure maps domain fields to database columns:

```java
public class RetailProductSQLField extends SQLField {
    // Maps DomainField to table.column with SQL type
    public static Map<DomainField, SQLField> get(DomainField field) { ... }
    public static Map<DomainField, SQLField> all() { ... }
}
```

### Entity Numbering Pattern

Entity IDs are generated using the numbering service:

```java
EntityNumberGenerator<T> - generates entity numbers
├── generate(T entity, LiveSequence seq)
EntityNumberGeneratorRegistry - looks up generators by entity type
PaddedPrefixGenerator - default: "RP" + "000010" = "RP000010"
```

### Query System Flow

```
HTTP Request (JSON)
       ↓
RequestQueryMapper
       ↓
DomainQuery (domain.query)
       ↓
DomainQuerySQLTranslator (infrastructure.query)
       ↓
SQLBuilder
       ↓
Parameterized SQL
```

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
