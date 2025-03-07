# JPA Entity Inheritance & Entity Mapping Notes

### Entity Inheritance in JPA

JPA provides different strategies to map an inheritance hierarchy to a database table. By default, when a subclass
extends a mapped superclass, JPA does not create a separate table for the subclass. Instead, it adds the subclass's
unique fields to the parent entity's table and introduces a **dtype** column to differentiate between different entity
types.

### Querying Subclass-Specific Data

To query subclass-specific data, a dedicated repository for the subclass must be implemented. The subclass repository
should define the necessary CRUD operations to interact with the database.

### How Data is Stored in the Database

Since `InheritanceType.SINGLE_TABLE` is used, JPA does not create separate tables for subclasses. Instead, their fields
are stored in the parent tale with a **dtype** column.

### Creating Corresponding Repositories for Subclass Queries

Even though all data is stored in the same table, querying subclass-specific data requires a dedicated repository.

### Key Takeaways

- JPA does not create separate tables fo subclasses by default. Instead, it adds subclass-specific fields to the parent
  entity's table.
- A **dtype** column is automatically added to parent table to distinguish which entity a row belongs to.
- To query subclass-specific data, a dedicated repository must be implemented for each subclass.
- **InheritanceType Strategies**:

#### `SINGLE_TABLE`

- default, best for performance but less normalization.
- **Best for**: Since all subclasses share the same table, there is less separation between them.
- **Downsides**: Cannot set NOT NULL on Child. Class Fields

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Book {
  // ... 
}
```

#### `JOINED`

- Each subclass has a separate table, and JPA uses **joins** to retrieve data.
- The **parent table** stores all common fields, which each **subclass table** stores its specific fields.
- Unlike the **SINGLE_TABLE** strateg, where all entities share a single table, the **JOINED** strategy maps each
  subclass to its own table.
- When calling **findAll** on the **parent repository**, JPA executes a **LEFT OUTER JOIN** query to fetch both parent
  and subclass entities.
- When calling **findAll** on a **child repository**, JPA performs an **INNER JOIN** to retrieve the subclass's fields
  along with the common fields from the parent table.

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {
  // ...
}

```

#### `TABLE_PER_CLASS`

- Each subclass has its own **independent table**, including both common fields from the parent and its specific fields.
- Unlike **JOINED** strategy, **no joins** are used since each subclass has a **fully separate table**.
- The **parent class does not have a corresponding table**; only subclass tables exist.
- When calling **findAll** on the **parent repository**, JPA performs a **UNION** query across all subclass tables.
- **Downsides**:
  - **Data redundancy**: common fields are duplicated across all subclass fields, leading to **higher storage usage**.
  - **ID auto-increment is not supported**: Each subclass must define its own unique ID generation strategy since
    there's no shared parent table to handle sequencing.

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Book {
  // ... 
}
```



