package org.education.faculty.config;

import org.education.faculty.dao.repository.DisciplineRepository;
import org.education.faculty.dao.repository.FacultyRepository;
import org.education.faculty.service.DisciplineService;
import org.education.faculty.service.FacultyService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;

@TestConfiguration
public class DisciplineServiceConfTest {
    @Bean
    @DependsOn("facultyService")
    public DisciplineService disciplineService(DisciplineRepository repository,
                                               FacultyService facultyService) {
        return new DisciplineService(repository, facultyService);
    }



    @Bean
    public FacultyService facultyService(FacultyRepository repository) {
        return new FacultyService(repository);
    }
}
