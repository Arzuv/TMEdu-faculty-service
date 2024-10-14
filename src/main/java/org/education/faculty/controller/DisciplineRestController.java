package org.education.faculty.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.education.faculty.dao.entity.Discipline;
import org.education.faculty.service.DisciplineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("v1/discipline")
public class DisciplineRestController {
    final DisciplineService disciplineService;

    @GetMapping
    public ResponseEntity<List<Discipline>> getAllDisciplines() {
        log.info("Fetching all faculties");
        List<Discipline> faculties = disciplineService.findAll();
        return ResponseEntity.ok(faculties);
    }
    
    @GetMapping("/byFaculty/{facultyId}")
    public ResponseEntity<List<Discipline>> getAllDisciplinesByFacultyId(@PathVariable String facultyId) {
        log.info("Fetching all faculties by faculty id {}", facultyId);
        List<Discipline> faculties = disciplineService.findAllByFacultyId(facultyId);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discipline> getDisciplineById(@PathVariable String id) {
        log.info("Fetching discipline with ID: {}", id);
        Optional<Discipline> discipline = disciplineService.findById(id);
        return discipline.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Discipline> createDiscipline(@RequestBody Discipline discipline) {
        log.info("Creating a new discipline: {}", discipline);
        Discipline createdDiscipline = disciplineService.save(discipline);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscipline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discipline> updateDiscipline(@PathVariable String id, @RequestBody Discipline discipline) {
        log.info("Updating discipline with ID: {}", id);
        Optional<Discipline> updatedDiscipline = disciplineService.update(id, discipline);
        return updatedDiscipline.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable String id) {
        log.info("Deleting discipline with ID: {}", id);
        if (disciplineService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
