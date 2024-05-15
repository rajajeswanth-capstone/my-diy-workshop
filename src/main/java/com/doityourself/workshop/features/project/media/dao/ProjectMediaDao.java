package com.doityourself.workshop.features.project.media.dao;

import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.database.repositories.DiyProjectLocalResourceRepository;
import com.doityourself.workshop.database.repositories.DiyProjectWebResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project Detail Dao
 */
@Repository
public class ProjectMediaDao {
  @Autowired
  DiyProjectLocalResourceRepository projectLocalResourceRepository;

  @Autowired
  DiyProjectWebResourceRepository projectWebResourceRepository;

  /**
   * Method to find project images by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectLocalResource}&gt;
   */
  public List<DiyProjectLocalResource> findProjectImagesByProjectId(Long projectId) {
    return projectLocalResourceRepository.findProjectLocalResourcesByProjectId(projectId);
  }

  /**
   * Method to find project web resources by project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&lt;{@link DiyProjectWebResource}&gt;
   */
  public List<DiyProjectWebResource> findProjectWebResourcesByProjectId(Long projectId) {
    return projectWebResourceRepository.findProjectWebResourcesByProjectId(projectId);
  }

  /**
   * Method to save project local resource
   *
   * @param diyProjectLocalResource {@link DiyProjectLocalResource}
   * @return {@link DiyProjectLocalResource}
   */
  public DiyProjectLocalResource saveProjectLocalResource(DiyProjectLocalResource diyProjectLocalResource) {
    return projectLocalResourceRepository.save(diyProjectLocalResource);
  }

  /**
   * Method to save project web resource
   *
   * @param projectWebResource {@link DiyProjectWebResource}
   * @return {@link DiyProjectWebResource}
   */
  public DiyProjectWebResource saveProjectWebResource(DiyProjectWebResource projectWebResource) {
    return projectWebResourceRepository.save(projectWebResource);
  }

  /**
   * Method to get project local resource by id
   *
   * @param id {@link Long}
   * @return {@link DiyProjectLocalResource}
   */
  public DiyProjectLocalResource findProjectLocalResourceByLocalResourceId(Long id) {
    return projectLocalResourceRepository.findById(id).orElse(null);
  }

  /**
   * Method to get project web resource by id
   *
   * @param id {@link Long}
   * @return {@link DiyProjectWebResource}
   */
  public DiyProjectWebResource findProjectWebResourceByWebResourceId(Long id) {
    return projectWebResourceRepository.findById(id).orElse(null);
  }

  /**
   * Method to delete project local resource by id
   *
   * @param id {@link Long}
   */
  public void deleteProjectLocalResourceByLocalResourceId(Long id) {
    projectLocalResourceRepository.deleteById(id);
  }

  /**
   * Method to delete project web resource by id
   *
   * @param id {@link Long}
   */
  public void deleteProjectWebResourceByWebResourceId(Long id) {
    projectWebResourceRepository.deleteById(id);
  }

  /**
   * Method to get distinct web resource types for a given project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&t;{@link String}&gt;
   */
  public List<String> findDistinctProjectWebResourceTypesByProjectId(Long projectId) {
    return projectWebResourceRepository.findDistinctProjectWebResourceTypesByProjectId(projectId);
  }

  /**
   * Method to get distinct local resource types for a given project id
   *
   * @param projectId {@link Long}
   * @return {@link List}&t;{@link String}&gt;
   */
  public List<String> findDistinctProjectLocalResourceTypesByProjectId(Long projectId) {
    return projectLocalResourceRepository.findDistinctProjectLocalResourceTypesByProjectId(projectId);
  }
}
