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

- **Routes** – define HTTP endpoints
- **Controllers** – handle request/response mapping
- **Domain layer** – core business logic and entities
- **Infrastructure layer** – database, Redis, JWT, configuration
- **Custom exception hierarchy** – clear distinction between API, domain and internal errors

Database access uses:

- MySQL
- HikariCP for pooled connections
- Explicit transaction handling

Authentication is handled using JWT.

Redis is used for caching constants and frequently accessed data.

---

## Status

This project is still evolving. The structure, conventions, and internal patterns may change as the system grows.

The main goal is to build something understandable, maintainable, and real, not just functional.
