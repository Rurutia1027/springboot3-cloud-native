package com.cloud.bookshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String province;
    private String city;
    private String area;
    private String address;
    private String zipcode;
}
