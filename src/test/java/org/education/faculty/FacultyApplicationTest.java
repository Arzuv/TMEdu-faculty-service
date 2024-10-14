package org.education.faculty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test-application")
public class FacultyApplicationTest {
    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        SpringApplication.from(FacultyApplication::main).run(args);
    }
}
