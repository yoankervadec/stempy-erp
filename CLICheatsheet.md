# DEV CLI Cheatsheet

## Docker

**List containers**

```sh
docker ps
```

**Enter a container**

```sh
docker exec -it <container_name> sh
```

---

## Redis

**Connect to Redis CLI (inside container)**

```sh
redis-cli
```

**List or Scan all keys**

```sh
KEYS *
SCAN 0
```

**Get value**

```sh
GET <key>
```

**Set value**

```sh
SET <key> <value>
```

**Delete key**

```sh
DEL <key>
```

**Clear all keys (current DB)**

```sh
FLUSHDB
```

**Clear everything (all databases)**

```sh
FLUSHALL
```

**Monitor live commands**

```sh
MONITOR
```

**Check key type**

```sh
TYPE <key>
```

**Check TTL**

```sh
TTL <key>
```

**Check memory usage**

```sh
INFO memory
```

---

## Maven

**Clean & compile:**

```sh
mvn clean compile
```

**Install to local repository**

```sh
mvn install
```

**Dependency tree**

```sh
mvn dependency:tree
```

**Check for dependency updates**

```sh
mvn versions:display-dependency-updates
```

**Effective POM (debugging)**

```sh
mvn help:effective-pom
# shove it in a file:
mvn help:effective-pom > effective-pom.xml
```

## General

**Check who's hogging the fjkhsds port**

```sh
lsof -i :7070
```
