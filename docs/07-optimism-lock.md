# Optimistic Locking in JPA

## Overview of Optimistic Locking

Optimistic locking is a strategy that allows multiple transactions to access the same data concurrently without locking
it. Instead of immediately locking the data, optimistic locking checks for data consistency only when the transaction is
committed. If any other transaction has modified the data during the course of the current transaction, an exception (
e.g., OptimisticLockException) is thrown, indicating a conflict. This mechanism is useful in systems where data
conflicts re expected to be rare but can be handled appropriately when they occur.

## How Optimistic Locking Works:

In optimistic locking, the idea is to allow multiple transactions to read the same data but ensure that they cannot
commit changes to the same record unless no other transaction has modified t in the meantime.

The key concept in optimistic locking is a **version** field (or **timestamp**) that is included in the entity. Each
time the entity is updated, the version is incremented. When a transaction attempts to update the entity, it checks the
version number in the database to see if it matches the version it had when it first read the entity. If they don't
match, it indicates that another transaction has modifies the entity in the meantime, and a conflict occurs.

## Implementing Optimistic Locking in JPA
### Using `@Version` Annotation 

In JPA, optimistic locking is typically implemented by using the `@Version` annotation. This annotation is placed on a
field (often called version) in the entity class. JPA automatically manages the versioning process, incrementing the
version value with every update.

```java
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;

  // This is the optimistic version field 
  @Version
  private Long version;
}
```

- The `@Version` field is used by JPA to keep track of the version number of the entity.
- Every time an update is made, the version is automatically incremented by JPA.
- If another transaction modifies the entity before the current one commits, the version mismatch will trigger an
  exception.

### Example Code for Optimistic Locking

- An example demonstrating how optimistic locking is used in practice

```java
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Transactional
public void updateBook(Long id, String newTitle) {
  // simple business logic here 
  book.setTitle(newTitle);

  try {
    // throw OptimisticLockException if version mismatch
    bookRepository.save(book);
  } catch (OptimisticLockException e) {
    // error logs 
  }
}
```

### Transaction Management

Optimistic locking doesn't lock the database record immediately. It allows the record to be read and updated
concurrently by other transactions. The actual locking only happens when trying to commit the changes. If any conflict
is detected due to a version mismatch, an exception is thrown, and the application must handle it, typically by retrying
or notifying the user.

---

## Downsides of Optimistic Locking

### Conflict Detection

- Optimistic locking does not prevent conflicts in real time. Conflicts are only detected when attempting to commit the
  transaction. This means that system might allow multiple transactions to modify the same data concurrently, only to
  detect the issue when committing.

### Retry Logic

- When a conflict occurs, the application must handle it by either retrying the operation or altering the user. This
  introduces additional complexity to the application, especially in high-concurrency environments.

### Not suitable for All Scenarios

- Optimistic locking is ideal when conflicts are rare. However, in highly concurrent applications where conflicts are
  frequent, pessimistic locking or other strategies might be more suitable.

### Versioning Overhead

- The versioning mechanism adds overhead to the entry, as every update requires checking and updating the version field.
  This can be problematic in scenarios where entities undergo frequent changes or are involved in complex operations.

---

## Best Practice for Optimistic Locking

### Use When Conflicts are Rare

- Optimistic locking works best in applications where data conflicts are expected to be rare. If conflicts are frequent,
  this strategy might introduce unnecessary complexity.

### Handle OptimisticLockException

- Always handle the `OptimisticLockException` appropriately. This could involve retrying the transaction or alerting the
  user to the conflict.

### Versioning Field Best Practices

- The version field should be an integer or long type to easily increment with each update.
- Avoid making the version field nullable; it should always be initialized with a value (e.g., 0 for new entities).

### Keep Transactions Short

- Try to keep transactions short and focused to reduce likelihood of conflicts. The longer the transaction, the higher
  the chances of conflicts occurring.

### Use @Version Carefully

- Use the `@Version` field for optimistic locking but be mindful of the types of fields use. The version should not be a
  business field and should not be used for things like timestamps or user-controlled data.

## Conclusion

Optimistic locking is a concurrency mechanism that avoids locking resources until absolutely necessary. It allows high
concurrency but introduces challenges when conflicts occur. By using the `@Version` annotation in JPA, optimistic
locking helps ensure data consistency when multiple transactions are accessing the same entity concurrently. However, it
requires handling potential conflicts and can introduce complexity in conflict resolution. Best practices suggest using
it in low-conflict scenarios and ensuring proper conflict handling in the application.
 