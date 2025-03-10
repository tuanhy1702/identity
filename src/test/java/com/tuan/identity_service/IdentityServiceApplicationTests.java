package com.tuan.identity_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/test.properties") // dùng H2 thay cho Mysql
class IdentityServiceApplicationTests {

    @Test
    void contextLoads() {}
}
