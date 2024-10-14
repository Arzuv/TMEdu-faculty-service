package org.education.faculty.service;

import org.education.faculty.config.DisciplineServiceConfTest;
import org.education.faculty.dao.entity.Discipline;
import org.education.faculty.dao.entity.Faculty;
import org.education.faculty.dto.DisciplineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataRedisTest
@ContextConfiguration(classes = DisciplineServiceConfTest.class)
public class DisciplineServiceTest extends RedisServiceTest {
    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineService disciplineService;

    private Discipline testDiscipline;
    private Faculty testFaculty;

    @BeforeEach
    void setUp() {

        testFaculty = Faculty.builder()
                .id(UUID.randomUUID())
                .name("IT")
                .build();

        testDiscipline = Discipline
                .builder()
                .id(UUID.randomUUID())
                .facultyId(testFaculty.getId())
                .name("Java")
                .build();

        disciplineRepository.save(testDiscipline);
    }

    @Test
    void testFindAll() {
        List<Discipline> disciplines = disciplineService.findAll();
        assertFalse(disciplines.isEmpty(), "Discipline list should not be empty");
        assertEquals(1, disciplines.size(), "There should be exactly one discipline");
    }

    @Test
    void testFindAllByFacultyId() {
        UUID facultyId = testFaculty.getId();
        List<Discipline> disciplines = disciplineService.findAllByFacultyId(facultyId.toString());

        assertFalse(disciplines.isEmpty(), "Discipline list should not be empty");
        assertEquals(1, disciplines.size(), "There should be exactly one discipline");
    }

    @Test
    void testFindById() {
        Optional<Discipline> found = disciplineService.findById(testDiscipline.getId().toString());
        assertTrue(found.isPresent(), "Discipline should be found");
        assertEquals(testDiscipline.getName(), found.get().getName());
    }

    @Test
    void testSave() {
        Discipline newDiscipline = Discipline.builder()
                .name("Physics")
                .facultyId(UUID.randomUUID())
                .build();

        Discipline savedDiscipline = disciplineService.save(newDiscipline);
        assertNotNull(savedDiscipline.getId(), "Saved discipline should have an ID");
        assertEquals("Physics", savedDiscipline.getName());
    }

    @Test
    void testUpdate() {
        Discipline updatedDiscipline = Discipline.builder()
                .name("Advanced Mathematics")
                .build();

        Optional<Discipline> updated = disciplineService.update(testDiscipline.getId().toString(), updatedDiscipline);
        assertTrue(updated.isPresent(), "Discipline should be updated");
        assertEquals("Advanced Mathematics", updated.get().getName());
    }

    @Test
    void testDeleteById() {
        boolean isDeleted = disciplineService.deleteById(testDiscipline.getId().toString());
        assertTrue(isDeleted, "Discipline should be deleted");

        Optional<Discipline> deletedDiscipline = disciplineService.findById(testDiscipline.getId().toString());
        assertFalse(deletedDiscipline.isPresent(), "Discipline should no longer exist");
    }
}
