package org.education.faculty.config;

import org.education.faculty.dto.DisciplineRepository;
import org.education.faculty.service.DisciplineService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
public class DisciplineServiceConfTest {
    @Bean
    public DisciplineService disciplineService(DisciplineRepository repository) {
        return new DisciplineService(repository);
    }
}
