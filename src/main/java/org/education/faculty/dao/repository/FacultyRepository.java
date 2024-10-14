package org.education.faculty.dao.repository;

import org.education.faculty.dao.entity.Faculty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, UUID> {
}
