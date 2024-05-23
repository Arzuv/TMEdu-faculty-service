package org.education.faculty.dao.repository;

import org.education.faculty.dao.entity.Discipline;
import org.education.faculty.dao.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {
}
