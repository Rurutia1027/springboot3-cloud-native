package com.cloud.bookshop.data.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String province;
    private String city;
    private String area;
    private String address;
    private String zipcode;
}
