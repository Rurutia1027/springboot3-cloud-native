package com.cloud.bookshop;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


// BaseTest should be extended by all businesses test cases
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BaseTest {
}
