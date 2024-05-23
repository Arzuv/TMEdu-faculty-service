package org.education.faculty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FacultyApplication {
    public static void main(String[] args) {
        SpringApplication.run(FacultyApplication.class, args);
    }
}