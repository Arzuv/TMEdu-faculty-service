package org.education.faculty.service;


import org.education.faculty.config.FacultyServiceConfTest;
import org.education.faculty.dao.entity.Faculty;
import org.education.faculty.dao.repository.FacultyRepository;
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
@ContextConfiguration(classes = FacultyServiceConfTest.class)
public class FacultyServiceTest extends RedisServiceTest {
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyService facultyService;

    private Faculty testFaculty;

    @BeforeEach
    void setUp() {
        testFaculty = Faculty.builder()
                .id(UUID.randomUUID())
                .name("Test Faculty")
                .build();


        facultyRepository.save(testFaculty);
    }

    @Test
    void findAll_ShouldReturnAllFaculties() {
        List<Faculty> faculties = facultyService.findAll();
        assertFalse(faculties.isEmpty());
        assertEquals(1, faculties.size());
        assertEquals(testFaculty.getId(), faculties.get(0).getId());
    }

    @Test
    void findById_ShouldReturnFaculty_WhenExists() {
        Optional<Faculty> faculty = facultyService.findById(testFaculty.getId().toString());
        assertTrue(faculty.isPresent());
        assertEquals(testFaculty.getId(), faculty.get().getId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        Optional<Faculty> faculty = facultyService.findById(UUID.randomUUID().toString());
        assertFalse(faculty.isPresent());
    }

    @Test
    void save_ShouldSaveFaculty_WhenNew() {
        Faculty newFaculty = new Faculty();
        newFaculty.setName("New Faculty");

        Faculty savedFaculty = facultyService.save(newFaculty);

        assertNotNull(savedFaculty.getId());
        assertEquals(newFaculty.getName(), savedFaculty.getName());

        Optional<Faculty> retrievedFaculty = facultyService.findById(savedFaculty.getId().toString());
        assertTrue(retrievedFaculty.isPresent());
        assertEquals(savedFaculty.getId(), retrievedFaculty.get().getId());
    }

    @Test
    void update_ShouldUpdateFaculty_WhenExists() {
        testFaculty.setName("Updated Faculty");
        Optional<Faculty> updatedFaculty = facultyService.update(testFaculty.getId().toString(), testFaculty);

        assertTrue(updatedFaculty.isPresent());
        assertEquals("Updated Faculty", updatedFaculty.get().getName());
    }

    @Test
    void update_ShouldReturnEmpty_WhenNotExists() {
        Faculty nonExistentFaculty = new Faculty();
        nonExistentFaculty.setId(UUID.randomUUID());
        nonExistentFaculty.setName("Non-existent Faculty");

        Optional<Faculty> updatedFaculty = facultyService.update(nonExistentFaculty.getId().toString(), nonExistentFaculty);
        assertFalse(updatedFaculty.isPresent());
    }

    @Test
    void deleteById_ShouldReturnTrue_WhenExists() {
        boolean deleted = facultyService.deleteById(testFaculty.getId().toString());
        assertTrue(deleted);

        assertFalse(facultyRepository.existsById(testFaculty.getId()));
    }

    @Test
    void deleteById_ShouldReturnFalse_WhenNotExists() {
        boolean deleted = facultyService.deleteById(UUID.randomUUID().toString());
        assertFalse(deleted);
    }
}
