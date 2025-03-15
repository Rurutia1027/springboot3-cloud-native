package com.cloud.bookshop.data;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


// BaseTest should be extended by all businesses test cases
@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Transactional
public class BaseTest {
}
