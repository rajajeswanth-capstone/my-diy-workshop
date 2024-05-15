package com.doityourself.workshop.database.repositories;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.database.entities.DiyUserProjectId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Diy User Project Repository
 */
@Repository
public interface DiyUserProjectRepository extends CrudRepository<DiyUserProject, DiyUserProjectId> {
  /**
   * Method to find diy projects by username that are not shared
   *
   * @param userName {@link String}
   * @return {@link List}&lt;{@link DiyProject}&gt;
   */
  @Query("select dup.id.diyProject from DiyUserProject dup where dup.id.diyUser.userName = ?1 and dup.shared = false")
  List<DiyProject> findProjectsByUserName(String userName);

  /**
   * Method to find shared diy projects by username
   *
   * @param userName {@link String}
   * @return {@link List}&lt;{@link DiyProject}&gt;
   */
  @Query("select dup.id.diyProject from DiyUserProject dup where dup.id.diyUser.userName = ?1 and dup.shared = true")
  List<DiyProject> findSharedProjectsByUserName(String userName);
}
