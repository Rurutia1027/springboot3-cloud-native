package com.cloud.bookshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class AuthorInfo extends DomainImpl{

    private String school;

    // here we declare info means let Author's info field
    // to manipulate the one-2-one relationship
    // and current AuthorInfo hands over its ownership
    @OneToOne(mappedBy = "info")
    private Author author;
}
