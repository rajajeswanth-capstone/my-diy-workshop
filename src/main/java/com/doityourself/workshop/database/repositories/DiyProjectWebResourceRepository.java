package com.doityourself.workshop.database.repositories;

import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Diy Project Web Resource Repository
 */
@Repository
public interface DiyProjectWebResourceRepository extends CrudRepository<DiyProjectWebResource, Long> {
  /**
   * Method to get project web resources by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectWebResource}&gt;
   */
  @Query("select wr from DiyProjectWebResource wr where wr.diyProject.id = ?1 and wr.type = 'WEB_RESOURCE'")
  List<DiyProjectWebResource> findProjectWebResourcesByProjectId(Long projectId);

  /**
   * Method to get distinct resource types for a given project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&t;{@link String}&gt;
   */
  @Query("select DISTINCT(wr.resourceType) from DiyProjectWebResource wr where wr.diyProject.id = ?1 and wr.type = 'WEB_RESOURCE'")
  List<String> findDistinctProjectWebResourceTypesByProjectId(Long projectId);
}
