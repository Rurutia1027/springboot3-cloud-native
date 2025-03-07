# FetchType vs. @NamedEntityGraph in JPA

### FetchType.EAGER (Always Load Data)

- Automatically loads the related entity whenever the main entity is fetched.
- Cannot be controlled dynamically - always joins the related entity.
- **Best for**: Cases where the related entity is **always needed**.
- **Downside**: Can cause necessary performance overhead.

```java
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@ManyToOne(fetch = FetchType.EAGER)
```

### FetchType.LAZY

### `@NamedEntityGraph` Loads Data When Needed

- Allows selective fetching of related entities **only when requested**.
- Works dynamically -- avoids unnecesary joins in queries.
- **Best for**: where the related entity is **sometimes needed**.

```java
import jakarta.persistence.NamedAttributeNode;

@NamedEntityGraph(name = "Book.withCategory", attributeNode = @NamedAttributeNode(("category")))
```

- **Repository Usage**

```java
import org.springframework.data.jpa.repository.EntityGraph;

@EntityGraph(value = "Book.withCategory")
@Query("SELECT b FROM Book b WHERE b.id=:id")
Optional<Book> findBookWithCategory(@Param("id") Long id); 
```

### Alternative: JOIN FETCH (Best for Performance)

- Explicitly fetches related entities only when needed.
- More efficient than `@NamedEntityGraph`
- **Best for**: Optimizing queries manually.

```java

@Query("SELECT b FROM Book b JOIN FETCH b.category WHERE b.id = :id")
Optional<Book> findBookWithCategory(@Param("id") Long id); 
```

### Best Practice Suggestions

- **Use FetchType.LAZY(default)** and fetch data onl when needed.
- **Use @EntityGraph** for flexible fetching.
- **use JOIN FETCH** for high-performance queries.
- **Avoid FetchType.EAGER** unless always necessary.