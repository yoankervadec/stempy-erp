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

### Middleware (`app.middleware`)

Responsible for:

- Authentication and authorization
- Building the `ApiRequest` object
- Preparing contextual data before the request reaches the controller

This layer ensures every request is validated and enriched with the required security and contextual information.

### Routes (`app.routes`)

Responsible for:

- Defining HTTP endpoints
- Mapping URLs and HTTP methods to controllers

Routes contain no business logic, they only connect incoming requests to the appropriate controller.

### Controllers (`app.controller`)

Responsible for:

- Handling request and response mapping
- Consuming the `ApiRequest` object
- Delegating execution to the appropriate facade

Controllers act as the boundary between the HTTP layer and the application logic.

### Facades (`app.facade`)

Responsible for:

- Coordinating high-level business workflows
- Managing database connections and transactional boundaries
- Performing context-aware validations
- Orchestrating one or multiple services

Facades represent complete business operations from the application’s perspective.

### Services (`core.service`)

Responsible for:

- Implementing focused, single use-case business logic
- Validating rules specific to one operation

Services are reusable and independent units of domain behavior.

### Repositories (`core.repository`)

Responsible for:

- Database interaction
- Mapping database records to domain objects

Repositories contain no business logic, only persistence concerns.

---

## Status

This project is still evolving. The structure, conventions, and internal patterns may change as the system grows.

The main goal is to build something understandable, maintainable, and real, not just functional.
