package com.doityourself.workshop.database.repositories;

import com.doityourself.workshop.database.entities.DiyProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Diy Project Repository
 */
@Repository
public interface DiyProjectRepository extends CrudRepository<DiyProject, Long> {
}
