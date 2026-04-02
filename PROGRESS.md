# Progress Log

#### SQL & Database Layer

- [x] Implemented SQLExecutor for executing queries
- [x] Built SQLBuilder for dynamic SQL construction with parameter binding
- [x] Fixed parameter mismatch issues in prepared statements
- [x] Standardized insert patterns (including generated keys handling)
- [x] Improved batch insert handling approach
- [ ] Add logging around SQL execution (timing, failures)

#### Query System

- [x] Completed DomainQuery structure (filters, sorting, pagination)
- [x] Implemented filter tree (AND / OR groups)
- [x] Built DomainQuerySQLTranslator
- [x] Added field validation to prevent SQL injection
- [x] Documented the full query system (docs/query.md)
- [ ] Consider fully dynamic query generation for basic SELECT / INSERT / UPDATE instead of relying on query cache

#### Automation System

- [x] Implemented job execution framework
- [x] Added job queue with thread-safe processing
- [x] Built worker thread system
- [x] Implemented scheduling (interval + fixed time)
- [x] Connected job system with dependency injection

#### Date & Time Handling

- [x] Migrated from LocalDateTime to Instant
- [x] Updated utility classes for UTC-safe handling
- [x] Started building helpers for parsing structured values (e.g., day-of-week arrays)

#### General Fixes & Improvements

- [x] Fixed incorrect table reference causing SQL errors
- [x] Improved debugging workflow for SQL issues
- [x] Cleaned up repository insert logic
- [ ] Standardize error handling across repositories and services
- [ ] Define error codes strategy (domain vs infrastructure)
- [ ] Define service patterns more strictly (facade vs service split)
- [ ] Implement authorization with RBAC/ABAC permissions (use annotations?)
- [ ] Review middleware chain
- [x] Test
