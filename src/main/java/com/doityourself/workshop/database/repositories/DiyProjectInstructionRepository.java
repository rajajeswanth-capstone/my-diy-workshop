package com.doityourself.workshop.database.repositories;

import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Diy Project Instruction Repository
 */
@Repository
public interface DiyProjectInstructionRepository extends CrudRepository<DiyProjectInstruction, Long> {
  /**
   * Method to get project instructions by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectInstruction}&gt;
   */
  @Query("select dpi from DiyProjectInstruction dpi where dpi.diyProject.id = ?1")
  List<DiyProjectInstruction> findProjectInstructionsByProjectId(Long projectId);
}
