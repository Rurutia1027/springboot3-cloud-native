# Design Doc

## E-R Diagram

In bookstore this project we have defined multiple domain entities: Author, AuthorInfo, Book, BookAuthor, Category.

Their E-R Relationships are shown in the following diagrams.

### Entity Relationship: Author - Address (1:1)

The relationship between **Author** and **Address** is a **composition**, where Address is a value object that belongs
to Author. We use `@Embedded` in Author to include **Address** as part of the same table, and `@Embeddable` in Address
to define it as a reusable value type. This approach avoids extra joins and keeps related data together.

### Entity Relationship: Category - Book (1:N)

In JPS, the best practice for a **one-to-many** relationship without generating an extra join table is manage the
foreign key form the **many-side (Book)** while keeping the **one-side(Category) unaware of relationship**. This avoids
unnecessary complexity and ensures efficient data retrieval.

Setting up a **bidirectional** relationship between `Category` and `Book`.

#### `Category.java`

- **mappedBy='category'** tells JPA that the Category entity's relationship to Book is already defined on the **Book
  side**.
- This means the **category** this field in Book will handle the foreign key(category_id), and JPA will not create a
  middle table or duplicate the relationship on the Category side.
- In other words, it is the **Book(many-side)** owned the relationship and maintain/record the relationship by foreign
  keys.
- Since the relationship is recorded as FK in Book table, Category side can get access to its associated multiple Books
  via this variable(bookList).
- Once we choose to let the many-side(Book) own the relationship with a foreign key in the table, we need to define how
  modifications on the one-side will affect the many-side, and how many-side's modification will be synchronized to the
  one side.
- **OrphanRemoval**: Ues `orphanRemoval=true` in @OneToMany to automatically delete Book entities when they are removed
  from the bookList.
- **Cascade**: Use `cascade=CascadeType.ALL` to propagate operations like PERSIST, MERGE, and REMOVE from Category to
  associated Book entities.

```java
import com.cloud.bookshop.domain.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Book> bookList; 
```

#### `Book.java`

- When adopting `@ManyToOne` annotation here, to enable bidirectional navigation.
- No extra join table(like `category_book`) is created automatically by JPA.
- **Category** does not **own** and **maintain** the relationship, so it doesn't need to track its associated books
  explicitly.

```java
import com.cloud.bookshop.domain.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@ManyToOne(fetch = FetchType.LAZY, optional = false)
private Category category; 
```

### Entity Relationship: Author: AuthorInfo(1:1)

In a one-to-one relationship, let the most frequently used side (in this case, the Author) manage the relationship.

- **Bidirectional Relationship**:
    - Use `@OneToOne` on both entities to make the relationship bidirectional.
    - On the Author side, the relationship is maintained, so we don't need to use mappedBy here.
- **Owning side**:
    - On the **AuthorInfo** side, we use `@OneToOne(mappedBy="info")` to indicate that `AuthorInfo` is the inverse side,
      and `Author` owns the relationship.
- **Cascade**: Cascade is also supported in OneToOne relationship in JPA.

- `Author.java`

```java
import com.cloud.bookshop.domain.AuthorInfo;
import jakarta.persistence.OneToOne;

@OneToOne
private AuthorInfo info; 
```

- `AuthorInfo.java`

```java
import jakarta.persistence.OneToOne;
// mappedBy means this relationship is handed over to Author side's Author#info this field
@OneToOne(mappedBy = "info")
private Author author; 
```

### Best Practice of One-to-Many Mapping in JPA

### Best Practice of Many-to-Many Mapping in JPA

### Best Practice of Embedded in JPA 