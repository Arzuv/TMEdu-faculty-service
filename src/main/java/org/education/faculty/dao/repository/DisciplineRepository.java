package org.education.faculty.dao.repository;

import org.education.faculty.dao.entity.Discipline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisciplineRepository extends CrudRepository<Discipline, UUID> {
}
