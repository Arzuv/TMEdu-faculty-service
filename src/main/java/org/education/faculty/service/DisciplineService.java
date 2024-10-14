package org.education.faculty.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.education.faculty.dao.entity.Discipline;
import org.education.faculty.dao.repository.DisciplineRepository;
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
public class DisciplineService {
    final DisciplineRepository disciplineRepository;

    public List<Discipline> findAll() {
        log.info("Fetching all disciplines from Redis");
        List<Discipline> disciplines = StreamSupport
                .stream(disciplineRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        log.info("Total disciplines fetched: {}", disciplines.size());
        return disciplines;
    }

    public List<Discipline> findAllByFacultyId(String facultyId) {
        log.info("Fetching all disciplines from Redis by facultyId {}", facultyId);
        UUID id = UUID.fromString(facultyId);
        List<Discipline> disciplines = StreamSupport
                .stream(disciplineRepository.findAll().spliterator(), false)
                        .filter(discipline -> discipline.getFacultyId().equals(id))
                                .collect(Collectors.toList());

        log.info("Total disciplines fetched: {}", disciplines.size());
        return disciplines;
    }

    public Optional<Discipline> findById(String id) {
        log.info("Fetching discipline with ID: {}", id);
        return disciplineRepository.findById(UUID.fromString(id));
    }

    public Discipline save(Discipline discipline) {
        log.info("Saving discipline: {}", discipline);
        if (Objects.isNull(discipline.getId())) {
            discipline.setId(UUID.randomUUID());
        }
        discipline = disciplineRepository.save(discipline);
        return discipline;
    }

    public Optional<Discipline> update(String id, Discipline discipline) {
        log.info("Updating discipline with ID: {}", id);
        if (disciplineRepository.existsById(UUID.fromString(id))) {
            discipline.setId(UUID.fromString(id));
            disciplineRepository.save(discipline);
            log.info("Successfully updated discipline with ID: {}", id);
            return Optional.of(discipline);
        } else {
            log.warn("Discipline with ID: {} not found for update", id);
            return Optional.empty();
        }
    }

    public boolean deleteById(String id) {
        log.info("Deleting discipline with ID: {}", id);
        if (disciplineRepository.existsById(UUID.fromString(id))) {
            disciplineRepository.deleteById(UUID.fromString(id));
            log.info("Successfully deleted discipline with ID: {}", id);
            return true;
        } else {
            log.warn("Discipline with ID: {} not found for deletion", id);
            return false;
        }
    }
}
