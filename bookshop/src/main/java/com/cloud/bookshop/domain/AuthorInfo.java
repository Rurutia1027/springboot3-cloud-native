package com.cloud.bookshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorInfo extends DomainImpl{

    private String school;

    // here we declare info means let Author's info field
    // to manipulate the one-2-one relationship
    // and current AuthorInfo hands over its ownership
    @OneToOne(mappedBy = "info")
    private Author author;
}
