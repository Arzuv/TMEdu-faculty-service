package org.education.faculty.config;

import org.education.faculty.dao.repository.DisciplineRepository;
import org.education.faculty.dao.repository.FacultyRepository;
import org.education.faculty.service.DisciplineService;
import org.education.faculty.service.FacultyService;
import org.education.faculty.setting.AuditorService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;

@TestConfiguration
public class DisciplineServiceConfTest {
    @Bean
    @DependsOn("facultyService")
    public DisciplineService disciplineService(DisciplineRepository repository,
                                               FacultyService facultyService,
                                               AuditorService auditorService) {
        return new DisciplineService(repository, facultyService, auditorService);
    }



    @Bean
    public FacultyService facultyService(FacultyRepository repository, AuditorService auditorService) {
        return new FacultyService(repository, auditorService);
    }

    @Bean
    public AuditorService auditorService(SpringSecurityAuditorAware auditorAware) {
        return new AuditorService(auditorAware);
    }

    @Bean
    public SpringSecurityAuditorAware auditorAware() {
        return new SpringSecurityAuditorAware();
    }


}
