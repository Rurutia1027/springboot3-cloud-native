# Primary Key Generation Strategies in JPA

### AUTO (`GenerationType.AUTO`)

- The provider (e.g., Hibernate) selects the strategy based on the database.
- **Downside**: Can vary behavior across different databases, potentially leading to inconsistency.

### IDENTITY (`GenerationType.IDENTITY`)

- The database automatically generates the primary key, usually via auto-increment.
- **Downside**: Limited control over the ID generation strategy and not suitable for databases that don't support
  auto-increment.

### SEQUENCE(`GenerationType.SEQUENCE`)

- Uses a database sequence to generate unique keys.
- **Downside**: May not be supported by all databases and requires additional configuration.

### TABLE (`GenerationType.TABLE`)

- A separate table stores and increments the primary key value.
- **Downside**: Performance overhead due to table locking and extra database operations.

## `ID_SEQUENCE` Code Breakdown

```java
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

@MappedSuperclass
@Data
public class DomainImpl {
    @GeneratedValue(generator = "sequenceGenerator")
    @GenericGenerator(name = "sequenceGenerator", strategy = "org.hibernate.id.enhance.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "ID_SEQUENCE"),
                    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
                    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = SequenceStyleGenerator.OPT_PARAM, value = "pooled")
            })
    @Id
    private Long id;
}
```

### Explanations

- `@GeneratedValue(generator="sequenceGenerator*)`
    - This annotation tells JPA/Hibernate that the value fo the **id** field will be automatically generated using a
      custom generator named **sequenceGenerator**. The generator's configuration is specified using the
      `@GenericGenerator` annotation.
- `@GenericGenerator(name="sequenceGenerator", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator...")`;
    - `@GenericGenerator` is a Hibernate-specific annotation used to define a custom generator.
    - The name attribute gives the generator a name ("sequenceGenerator") that is referenced in the `@GeneratedValue`
      annotation.
    - **strategy**: Specifies the strategy used for ID generation. In this case, it is the
      `org.hibernate.id.enhanced.SequenceStyelGenerator...`, which is a highly configurable sequence-based ID generator
      provided by Hibernate. This strategy allows fine-grained control over how sequences are handled.
- `@Parameter` Annotations:
    - The `@Parameter` annotations inside the `@GenericGenerator` define configuration settings for the sequence
      generator.
    - **SequenceStyleGenerator#SEQUENCE_PARAM**:
        - Defines the name of the database sequence used for ID generation. In this case, the sequence name is "
          ID_SEQUENCE". This sequence is expected to exist in the database, or Hibernate will automatically create it if
          it doesn't exist.
    - **SequenceStyleGenerator.INITIAL_PARAM**:
        - Specifies the initial value for the sequence. In this case, the sequence will start at 1000.
    - **SequenceStyleGenerator.INCREMENT_PARAM**:
        - Specifies the increment size for the sequence. In this case, the sequence will increment by 1 each time a new
          value is generated.
    - **SequenceStyleGenerator#OPT_PARAM**:
        - This is a performance optimization setting. The value "pooled" means that Hibernate will pool IDs in memory,
          which can improve performance when generating IDs for entities in bulk. This option helps reduce database
          round-trips when generating IDs for entities.

### How it Works

- Hibernate will use the ID_SEQUENCE sequence in the database to generate the id values for this entity. The sequence
  will start at 1000 and increment by 1 for each new entity.
- The "pooled" optimization means that Hibernate will pre-fetch a block of IDs in memory, reducing the number of
  database accesses required to generate the IDs.
- Every time you persist a new entity, Hibernate will fetch the next available ID form the sequence and assign it to the
  id field of the entity.

### Use Case:

- This setup is useful when you want to have control over the sequence generation strategy, particularly in distributed
  environments where performance is crucial. By using "pooled", you reduce the contention on the database sequence by
  caching multiple IDs locally.
- The sequence generator is appropriate when you want to use a database sequence and fine-tune its behavior, such as
  starting at a specific number, using incremental values, and optimizng for performance.

### Downside

- The pooled strategy can sometimes lead to gaps in the sequence numbers due to the way IDs are fetched in batches. For
  example, if a batch of IDs is pre-fetched but not used (due to a rollback or a failed transaction), the sequence will
  skip over those IDs.
- If the database sequence is heavily used, or if you have multiple nodes generating IDs, there could be a risk of
  sequence contention. However, this can be mitigated using the "pooled" strategy or through more advanced sequence
  configuration.

### Best Practices

- Always ensure that the database sequence (ID_SEQUENCE) exists before using this strategy, or rely on Hibernate
  auto-creation strategy feature.
- Choose the sequence name (ID_SEQUENCE) carefully avoid conflicts with other sequences in the database.
- Test the performance impact of the pooled strategy in your application, especially in distributed systems or in
  scenarios where high througput is required. 