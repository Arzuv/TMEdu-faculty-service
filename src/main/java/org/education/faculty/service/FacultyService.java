package org.education.faculty.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.education.faculty.dao.entity.Faculty;
import org.education.faculty.dao.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FacultyService {
    final FacultyRepository facultyRepository;

    public List<Faculty> findAll() {
        log.info("Fetching all faculties from Redis");
        List<Faculty> faculties = StreamSupport
                .stream(facultyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        log.info("Total faculties fetched: {}", faculties.size());
        return faculties;
    }

    public Optional<Faculty> findById(String id) {
        log.info("Fetching faculty with ID: {}", id);
        return facultyRepository.findById(UUID.fromString(id));
    }

    public Faculty save(Faculty faculty) {
        log.info("Saving faculty: {}", faculty);
        if (Objects.isNull(faculty.getId())) {
            faculty.setId(UUID.randomUUID());
        }
        faculty = facultyRepository.save(faculty);
        return faculty;
    }

    public Optional<Faculty> update(String id, Faculty faculty) {
        log.info("Updating faculty with ID: {}", id);

        if (facultyRepository.existsById(UUID.fromString(id))) {
            faculty.setId(UUID.fromString(id));
            faculty = facultyRepository.save(faculty);
            log.info("Successfully updated faculty with ID: {}", id);
            return Optional.of(faculty);
        } else {
            log.warn("Faculty with ID: {} not found for update", id);
            return Optional.empty();
        }
    }

    public boolean deleteById(String id) {
        log.info("Deleting faculty with ID: {}", id);
        if (isExistsFaculty(UUID.fromString(id))) {
            facultyRepository.deleteById(UUID.fromString(id));
            log.info("Successfully deleted faculty with ID: {}", id);
            return true;
        } else {
            log.warn("Faculty with ID: {} not found for deletion", id);
            return false;
        }
    }

    public boolean isExistsFaculty(UUID id) {
        log.info("Checking is exists the faculty by id: {}", id);
        return facultyRepository.existsById(id);
    }
}
