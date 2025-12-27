# Middleware

1. `JwtAuthntificationMiddleware`

- Fail early is unauthenticated
- Set `userNo` to `ctx.attribute()`

2. `ApiRequestMiddleware`

- Access methods such as `hasPayload()` from the start
- Set `ctx.attribute()`

3. `ServerContextMiddleware`

- Build server-side metadata
- Store `userNo` and fetch `User`

4. `RequestOptionsMiddleware`

- Get from headers and body

5. `AuthorizationMiddleware`

- idk yet...
