package com.doityourself.workshop.database.repositories;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Diy Project DiyProjectMaterial Repository
 */
@Repository
public interface DiyProjectMaterialRepository extends CrudRepository<DiyProjectMaterial, Long> {
  /**
   * Method to get project materials by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectMaterial}&gt;
   */
  @Query("select dpm from DiyProjectMaterial dpm where dpm.diyProject.id = ?1")
  List<DiyProjectMaterial> findProjectMaterialsByProjectId(Long projectId);

  /**
   * Method to get distinct local resource types for a given project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&t;{@link String}&gt;
   */
  @Query("select DISTINCT(dpm.vendor) from DiyProjectMaterial dpm where dpm.diyProject.id = ?1")
  List<String> findDistinctProjectVendorsByProjectId(Long projectId);
}
