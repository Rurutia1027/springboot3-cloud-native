package com.cloud.bookshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Author extends DomainImpl {
    private String name;

    @Column(columnDefinition = "INT(3)")
    private int age;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @ElementCollection
    private List<String> hobbies;

    @ElementCollection
    private List<Address> addresses;

    @OneToOne
    private AuthorInfo info;

    @OneToMany(mappedBy = "author")
    // @OrderBy("book.name DESC ")
    private List<BookAuthor> books;
}
