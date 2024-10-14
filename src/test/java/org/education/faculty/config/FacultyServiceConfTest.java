package org.education.faculty.config;

import org.education.faculty.dto.FacultyRepository;
import org.education.faculty.service.FacultyService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FacultyServiceConfTest {

    @Bean
    public FacultyService facultyService(FacultyRepository repository) {
        return new FacultyService(repository);
    }

}
