package de.nicolasschlecker.vvsmarthomeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SmartHomeServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
