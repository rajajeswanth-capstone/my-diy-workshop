package com.doityourself.workshop.features.project.detail.dao;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.database.repositories.DiyProjectInstructionRepository;
import com.doityourself.workshop.database.repositories.DiyProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project Detail Dao
 */
@Repository
public class ProjectDetailDao {
  @Autowired
  DiyProjectRepository projectRepository;

  @Autowired
  DiyProjectInstructionRepository projectInstructionRepository;

  /**
   * Method to find project by id
   *
   * @param id {@link Long}
   * @return {@link DiyProject}
   */
  public DiyProject findProjectById(Long id) {
    return projectRepository.findById(id).orElse(null);
  }

  /**
   * Method to find project instructions by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectInstruction}&gt;
   */
  public List<DiyProjectInstruction> findProjectInstructionsByProjectId(Long projectId) {
    return projectInstructionRepository.findProjectInstructionsByProjectId(projectId);
  }

  /**
   * Method to save project instruction
   *
   * @param projectInstruction {@link DiyProjectInstruction}
   * @return {@link DiyProjectInstruction}
   */
  public DiyProjectInstruction saveProjectInstruction(DiyProjectInstruction projectInstruction) {
    return projectInstructionRepository.save(projectInstruction);
  }

  /**
   * Method to get project instruction by id
   *
   * @param id {@link Long}
   * @return {@link DiyProjectInstruction}
   */
  public DiyProjectInstruction findProjectInstructionByInstructionId(Long id) {
    return projectInstructionRepository.findById(id).orElse(null);
  }

  /**
   * Method to delete project instruction by id
   *
   * @param id {@link Long}
   */
  public void deleteProjectInstructionByInstructionId(Long id) {
    projectInstructionRepository.deleteById(id);
  }
}
