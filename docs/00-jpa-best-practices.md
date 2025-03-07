# Best Practice for JPA Annotations

### Embedded Objects in JPA

- `@Embeddable` for the embedded object class.
- `@Embedded`for embedding the object in the parent entity.
- No lifecycle management, no `cascade` or `orphanRemoval` needed.
- Fetch strategy: N/A (embedded objects are part of the parent entity).

### Simple Type and Embedded Objects in Collections

- `@ElementCollection` for collections for simple type or embedded objects.
- Optionally use `@CollectionTable` for the collection's table.
- No `cascade` or `orphanRemoval`, managed as part of the parent.
- Fetch strategy: **LAZY** by default (as the collection is not a full entity).

### One-To-Many | Many-To-One

- `@ManyToOne` on the "many" side, `@OneToMany` on the "one" side.
- `mappedBy` avoids duplication of relationships. (_note: relationship duplication is different from bidirectional, the
  former one is referring to which side own the relationship, the latter one is referring to "one" side can get "many"
  side, and "many" side can also get access to "one" side_).
- `cascade = CascadeType.ALL` if change should propagate to the "many" side.
- `orphanRemoval = true` for automatic deletion of orphaned entities.
- `optional=false` for required relationship(on `@ManyToOne`).
- Fetch strategy: **LAZY** by default, **EAGER** if needed (e.g., for immediate loading).

### One-To-One

- `@OneToOne` with `@JoinColumn` or `@PrimaryKeyJoinColumn`.
- Use `cascade = CascadeType.ALL` to propagate operations.
- `orphanRemoval = true` for removing orphaned entities when the relationship is broken.
- `optional=false` for mandatory relationships.
- Fetch strategy: **EAGER** by default (for one-to-one relationship), but can be set to **LAZY** if needed.Ò

## Many-To-Many

- Avoid `@ManyoMany` directly; use a **join entity** with `@ManyToOne` on both sides.
- Use `cascade=CascadeType.ALL` to propagate changes across the relationship.
- `orphanRemoval = true` to clean up unlinked relationships in the join entity.
- `optional=false` on `@ManyToOne` for mandatory relationships.
- Fetch strategy: **LAZY* by default for many-to-many (to avoid loading all related entities at once).
