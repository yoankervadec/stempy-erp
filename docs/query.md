# Query System

This module provides a **generic query abstraction** that allows HTTP clients to send complex filtering, sorting, and pagination instructions which are safely translated into SQL.

The system separates concerns into three layers:

1. **HTTP Query Format** -> what the client sends
1. **Domain Query Model** -> a database-agnostic representation of the query
1. **SQL Translation** -> converts the domain query into SQL

This approach allows repositories to build dynamic queries without manually parsing request parameters or concatenating SQL strings.

---

## Problems This Solves

### 1. Dynamic Filtering Without Manual SQL Construction

Applications often need to support queries such as:

- filtering by multiple fields
- combining filters with AND / OR
- nested conditions
- comparison operators (=, >, LIKE, etc.)

Without a structured system, this usually leads to:

```java
if (name != null) {
   sql += " AND name LIKE ?";
}

if (createdAfter != null) {
   sql += " AND created_at > ?";
}
```

Problems with this approach:

- Difficult to maintain
- Hard to support nested logic
- Hard to reuse across endpoints
- Easy to introduce SQL injection risks

The query system solves this by representing filters as a **tree structure**.

---

### 2. Separation Between HTTP and Persistence Layers

Clients send queries as JSON.
Repositories operate on SQL.

This system introduces a **domain query layer** so that:

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
SQL
```

Each layer has a single responsibility.

---

### 3. Safe SQL Generation

The system ensures:

- SQL parameters are always bound
- column names are validated against a field map
- SQL injection via field names is impossible

If a client tries to filter on a non-allowed field:

`FieldNotFoundException: Invalid field`

---

## Core Components

### DomainQuery

Represents a query independent of HTTP or SQL.

```java
public record DomainQuery(
    FilterNode filters,
    List<SortSpec> sortSpec,
    PageSpec pageSpec
)
```

Contains:

| Field    | Description   |
| -------- | ------------- |
| filters  | Filter tree   |
| sortSpec | Sorting rules |
| pageSpec | Pagination    |

## Filter System

Filters are represented as a **tree structure**.

### FilterCondition

Represents a single comparison.

```java
public record FilterCondition(
    String field,
    ComparisonOperator operator,
    Object value
)
```

Example:

`price > 100`

### FilterGroup

Represents a group of filters joined by a logical operator.

```java
public record FilterGroup(
    LogicalOperator operator,
    List<FilterNode> children
)
```

Example:

`(price > 100 AND status = 'ACTIVE')`

Nested groups are supported.

### Logical Operators

```java
AND
OR
```

### Comparison Operators

Supported operators:

```java
EQUALS
NOT_EQUALS
LIKE
IN
GREATER_THAN
LESS_THAN
GREATER_OR_EQUAL
LESS_OR_EQUAL
IS_NULL
IS_NOT_NULL
BETWEEN
```

## Sorting

Sorting is defined with SortSpec.

```java
public record SortSpec(
    String field,
    boolean ascending
)
```

Exmaple:

`ORDER BY created_at DESC`

## Pagination

Pagination uses PageSpec.

```java
public record PageSpec(
    int page,
    int size
)
```

---

## HTTP Query Mapping

Clients send query instructions as JSON.

Example request:

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
          "field": "retailProductVariantNo",
          "comparison": "EQUALS",
          "value": "Black"
        },
        {
          "operator": "OR",
          "children": [
            {
              "field": "description",
              "comparison": "LIKE",
              "value": "product"
            },
            {
              "field": "name",
              "comparison": "LIKE",
              "value": "product"
            }
          ]
        }
      ]
    },
    "sorting": [
      {
        "field": "enabled",
        "direction": "DESC"
      }
    ],
    "pagination": {
      "page": 1,
      "size": 50
    },
    "preferences": {}
  }
}
```

`RequestQueryMapper` converts this into a `DomainQuery`.

---

## SQL Translation

`DomainQuerySQLTranslator` converts a `DomainQuery` into SQL using `SQLBuilder`.

It:

- validates fields
- binds parameters
- builds WHERE clauses recursively
- applies sorting
- applies pagination

---

## Example Repository Usage

### 1. Define Field Map

See `stempy-infrastructure` at `com.lesconstructionssapete.stempyerp.field`

Repositories define which fields are allowed.

```java
SQLField CREATED_BY_USER_ID = new SQLField(
    "createdByUserId",          // Logical Name (client-facing)
    DOM_RETAIL_PRODUCT_MASTER,  // SQL Table Name
    "created_by_user_id",       // SQL Column Name
    Types.BIGINT);              // SQL Type (java.sql.types)
```

```java
Map<String, SQLField> LOOKUP = Map.ofEntries(
    Map.entry(ID.logicalName(), ID),
    Map.entry(ENABLED.logicalName(), ENABLED),
    Map.entry(CREATED_AT.logicalName(), CREATED_AT)
    // ...
);
```

This prevents filtering on unknown fields.

---

### 2. Create SQL Builder

```java
SQLBuilder builder =
    new SQLBuilder(QueryCache.get(Query.SELECT_DOM_RETAIL_PRODUCT_MASTER));
```

---

### 3. Apply Query

```java
DomainQuerySQLTranslator translator =
    new DomainQuerySQLTranslator(LOOKUP);

translator.apply(builder, domainQuery);
```

---

### 4. Build SQL

```java
String sql = builder.build();
List<SQLBuilder.SQLParam> params = builder.getParams();
```

Example output:

```sql
SELECT *
FROM products p
WHERE (p.status = ? AND (p.price > ? OR p.category = ?))
ORDER BY p.created_at DESC
LIMIT 50
OFFSET 0
```

Parameters:

```json
["ACTIVE", 100, "TOOLS"]
```

---

## SQLBuilder

`SQLBuilder` provides a lightweight SQL construction utility.

Features:

- Named parameters (:name)
- Automatic parameter ordering
- Safe parameter binding
- Support for dynamic clauses

Supported clauses:

```
WHERE
JOIN
GROUP BY
HAVING
ORDER BY
LIMIT
OFFSET
```

Example:

```java
SQLBuilder builder =
    new SQLBuilder(QueryCache.get(Query.SELECT_DOM_RETAIL_PRODUCT_MASTER));

builder.where("retail_product_master.created_by_user_id = :createdByUserId");
builder.bind("createdByUserId", "US01");

String sql = builder.build();
```

Final SQL:

```sql
SELECT *
FROM retail_product_master
WHERE created_by_user_id > ?
```

---

## Advantages of This Approach

- Supports complex nested filters
- Prevents SQL injection on column names
- Keeps HTTP layer separate from persistence layer
- Repositories stay clean and readable
- Filters are fully composable and extensible
