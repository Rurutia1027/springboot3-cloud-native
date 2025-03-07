# Persistence Context in Spring Boot

The **Persistence Context** is the **first-level cache** in JPA that manages entity instances and tracks changes. It
ensures that entites within the same transaction remain **consistent**.

### Key Features

- **Dirty Checking**: Automatically detects and persists entity changes without explicit `save()` calls.
- **Transaction-Scoped Lifecycle**: The **Persistence Context** is bound to the transaction and cleared when the
  transaction ends.
- **Automatic Changes Tracking**: Managed entities are updated in the DB when the transaction commits.
- **Performance Optimization**: Avoids redundant queries by caching entities within the transaction.

### Spring Boot Annotations for Persistence Context Handling

```java
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@PersistenceContext
private EntityManager entityManager; 
```

### `@Transactional` -> Ensures operations run within a transaction.

```java
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceContext
private EntityManager entityManager;

@Transactional
public void saveBook(Book b) {
  entityManager.persist(b);
}
```

### `@EnableTransactionManagement` -> Enables annotation-based transactions.

---

## Repository Handling && JPA Persistence Consistency

All entities **values and status** manipulate through repository handlers(JpaRepository, CrudRepository, etc.) are *
*stored and managed** in the Persistence Context. This ensures that:

- Entities remain **consistent** within the transaction.
- **Dirty checking** automatically updates modified entities without explicit save operations.
- **Caching reduces redundant queries**, improving performance. 


