# TransactionManager (TransactionRunner)

## `REQUIRED`: One shared transaction

- Same connection
- Same commit / rollback
- Uses `ROLLBACK_ONLY`
- **All or nothing**

---

## `REQUIRES_NEW`: Fully independent transaction

- New connection
- Immediate commit/rollback
- No shared state
- **Fire-and-forget (unless you propagate exception)**

---

## `SUPPORTS`: Optional transaction

- Uses existing if present
- Otherwise runs without transaction

---

## Golden Rules

### 1. Never ignore exceptions inside `REQUIRED`

```java
try {
  doSomething();
} catch (Exception e) {}  // dangerous
```

Why:

- `ROLLBACK_ONLY` is already set
- The transaction will rollback anyway
- It's hiding a failure

---

### 2. Let exceptions propagate in `REQUIRED`

```java
execute(REQUIRED, conn -> {
  doCriticalWork();   // must succeed
  return null;
});
```

This ensures:

- Clean rollback
- No hidden inconsistent state

---

### 3. Use `REQUIRES_NEW` for non-critical side effects

Perfect use cases:

- Audit logs
- Notifications
- Background tracking
- Metrics
- Optional integrations

---

### 4. Always isolate `REQUIRES_NEW` with try/catch if optional

```java
execute(REQUIRED, outer -> {

  try {
    execute(REQUIRES_NEW, inner -> {
      logAudit();
      return null;
    });
  } catch (Exception e) {
    // log only, do NOT rethrow
  }

  return null;
});
```

Otherwise:

- Exception bubbles up
- Outer transaction fails (even though DB is independent)

---

### 5. Never mix critical logic into `REQUIRES_NEW`

Bad:

```java
execute(REQUIRES_NEW, conn -> {
  updateOrderStatus();  // critical business logic
});
```

Why:

- It commits even if outer fails
- It loses consistency

---

### 6. Keep business invariants inside `REQUIRED`

```java
execute(REQUIRED, conn -> {
  createOrder();
  reserveInventory();
  chargeCustomer();
  return null;
});
```

Everything succeeds or everything rolls back.

---

## Common Patterns

### Pattern 1: Core transaction

```java
execute(REQUIRED, conn -> {
  serviceA();
  serviceB();
  serviceC();
  return null;
});
```

&rarr; Safe\
&rarr; Consistent\
&rarr; Default choice

---

### Pattern 2: Side-effect isolation

```java
execute(REQUIRED, conn -> {

  createOrder();

  try {
    execute(REQUIRES_NEW, inner -> {
      audit("ORDER_CREATED");
      return null;
    });
  } catch (Exception ignored) {}

  return null;
});
```

&rarr; Audit never breaks main flow\
&rarr; Audit commits even if outer fails

---

### Pattern 3: Best-effort external call

```java
execute(REQUIRED, conn -> {

  updateDatabase();

  try {
    execute(REQUIRES_NEW, inner -> {
      callExternalApi();
      return null;
    });
  } catch (Exception e) {
    log.warn("External API failed", e);
  }

  return null;
});
```

---

### Pattern 4: Conditional rollback (understand behavior)

```java
execute(REQUIRED, conn -> {

  try {
    riskyOperation();
  } catch (Exception e) {
    // even if caught → transaction WILL rollback
  }

  return null;
});
```

&rarr; Because:

```java
markRollbackOnly();
```

---

## Anti-Patterns (avoid these)

### Swallowing exceptions in `REQUIRED`

```java
try {
  execute(REQUIRED, ...);
} catch (Exception ignored) {}
```

&rarr; You just hid a rollback &rarr; debugging nightmare

---

### Using `REQUIRES_NEW` for core logic

```java
execute(REQUIRES_NEW, conn -> saveOrder());
```

&rarr; Breaks consistency guarantees

---

### Calling `REQUIRED` inside `REQUIRES_NEW` expecting linkage

```java
execute(REQUIRES_NEW, conn -> {
  execute(REQUIRED, inner -> {
    // this joins REQUIRES_NEW, not outer
  });
});
```

&rarr; `REQUIRED` joins **current context**, not original one

---

## Subtle but IMPORTANT behaviors

### 1. `ROLLBACK_ONLY` is irreversible

Once set:

```java
markRollbackOnly();
```

&rarr; You CANNOT recover the transaction

---

### 2. `REQUIRES_NEW` commits even if outer fails

```java
execute(REQUIRED, outer -> {

  execute(REQUIRES_NEW, inner -> saveAudit());

  throw new RuntimeException();
});
```

&rarr; Audit &rarr; committed\
&rarr; Outer &rarr; rolled back

---

### 3. Exception propagation != transaction linkage

Even though transactions are independent:

```java
execute(REQUIRES_NEW, inner -> {
  throw new RuntimeException();
});
```

&rarr; Still propagates unless caught

---
