package com.doityourself.workshop.features.project.material.dao;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.database.repositories.DiyProjectMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project Material Dao
 */
@Repository
public class ProjectMaterialDao {
  @Autowired
  DiyProjectMaterialRepository projectInstructionRepository;

  /**
   * Method to find project materials by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectMaterial}&gt;
   */
  public List<DiyProjectMaterial> findProjectMaterialsByProjectId(Long projectId) {
    return projectInstructionRepository.findProjectMaterialsByProjectId(projectId);
  }

  /**
   * Method to save project material
   *
   * @param diyProjectMaterial {@link DiyProjectMaterial}
   * @return {@link DiyProjectMaterial}
   */
  public DiyProjectMaterial saveProjectMaterial(DiyProjectMaterial diyProjectMaterial) {
    return projectInstructionRepository.save(diyProjectMaterial);
  }

  /**
   * Method to get project material by id
   *
   * @param id {@link Long}
   * @return {@link DiyProjectMaterial}
   */
  public DiyProjectMaterial findProjectMaterialByMaterialId(Long id) {
    return projectInstructionRepository.findById(id).orElse(null);
  }

  /**
   * Method to delete project material by id
   *
   * @param id {@link Long}
   */
  public void deleteProjectMaterialByMaterialId(Long id) {
    projectInstructionRepository.deleteById(id);
  }

  /**
   * Method to get distinct vendors for a given project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&t;{@link String}&gt;
   */
  public List<String> findDistinctProjectVendorsByProjectId(Long projectId) {
    return projectInstructionRepository.findDistinctProjectVendorsByProjectId(projectId);
  }
}
