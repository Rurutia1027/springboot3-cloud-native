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
    - `SINGLE_TABLE`: default, best for performance but less normalization.
    - `TABLE_PER_CLASS`: each subclass gets its own table, no dtype column.
    - `JOINED`: separate tables for each subclass, but uses joins.