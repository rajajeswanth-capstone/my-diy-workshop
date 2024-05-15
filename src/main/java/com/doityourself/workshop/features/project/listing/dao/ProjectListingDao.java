package com.doityourself.workshop.features.project.listing.dao;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.database.repositories.DiyProjectRepository;
import com.doityourself.workshop.database.repositories.DiyUserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Project Listing Dao
 */
@Repository
public class ProjectListingDao {
  @Autowired
  DiyProjectRepository projectRepository;

  @Autowired
  DiyUserProjectRepository userProjectRepository;

  /**
   * Method to save project
   *
   * @param diyProject {@link DiyProject}
   * @return {@link DiyProject}
   */
  public DiyProject saveProject(DiyProject diyProject) {
    return projectRepository.save(diyProject);
  }

  /**
   * Method to save user project association
   *
   * @param diyUserProject {@link DiyUserProject}
   * @return {@link DiyUserProject}
   */
  public DiyUserProject saveUserProject(DiyUserProject diyUserProject) {
    return userProjectRepository.save(diyUserProject);
  }

  /**
   * Method to delete project by id
   *
   * @param id {@link Long}
   */
  public void deleteByProjectId(Long id) {
    projectRepository.findById(id).ifPresent(project -> projectRepository.delete(project));
  }
}
