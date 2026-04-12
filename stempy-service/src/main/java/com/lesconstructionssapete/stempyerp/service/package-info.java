/**
 * This package contains the service classes for the Stempy ERP system. The
 * service layer is responsible for implementing the core business logic of the
 * application. It acts as an intermediary between the presentation layer (or
 * facade layer) and the data access layer, ensuring that business rules are
 * consistently applied.
 * 
 * Single responsibility, atomic operations, basic validation, and delegation to
 * the data access layer. Services should not be concerned with transaction
 * management, context-aware error handling, or orchestration of multiple
 * services, as these responsibilities are typically handled by the facade
 * layer.
 */
package com.lesconstructionssapete.stempyerp.service;
