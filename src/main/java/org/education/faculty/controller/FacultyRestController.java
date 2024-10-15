package org.education.faculty.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.education.faculty.dao.entity.Faculty;
import org.education.faculty.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("v1/faculty")
public class FacultyRestController {
    final FacultyService facultyService;

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        log.info("Fetching all faculties");
        List<Faculty> faculties = facultyService.findAll();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable String id) {
        log.info("Fetching faculty with ID: {}", id);
        Optional<Faculty> faculty = facultyService.findById(id);
        return faculty.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        log.info("Creating a new faculty: {}", faculty);
        Faculty createdFaculty = facultyService.save(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable String id, @RequestBody Faculty faculty) {
        log.info("Updating faculty with ID: {}", id);
        Optional<Faculty> updatedFaculty = facultyService.update(id, faculty);
        return updatedFaculty.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable String id) {
        log.info("Deleting faculty with ID: {}", id);
        if (facultyService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
