package com.doityourself.workshop.database.repositories;

import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Diy Project Local Resource Repository
 */
@Repository
public interface DiyProjectLocalResourceRepository extends CrudRepository<DiyProjectLocalResource, Long> {
  /**
   * Method to get project local resources by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectLocalResource}&gt;
   */
  @Query("select dpi from DiyProjectLocalResource dpi where dpi.diyProject.id = ?1 and dpi.type = 'LOCAL_RESOURCE'")
  List<DiyProjectLocalResource> findProjectLocalResourcesByProjectId(Long projectId);

  /**
   * Method to get distinct local resource types for a given project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&t;{@link String}&gt;
   */
  @Query("select DISTINCT(wr.resourceType) from DiyProjectLocalResource wr where wr.diyProject.id = ?1 and wr.type = 'LOCAL_RESOURCE'")
  List<String> findDistinctProjectLocalResourceTypesByProjectId(Long projectId);
}