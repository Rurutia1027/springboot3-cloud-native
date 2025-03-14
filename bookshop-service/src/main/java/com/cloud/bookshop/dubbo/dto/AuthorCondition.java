package com.cloud.bookshop.dubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorCondition {
    private String name;

    // age query in a given range
    private Integer age;
    private Integer ageTo;
    private Gender gender;
}
