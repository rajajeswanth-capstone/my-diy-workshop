package com.doityourself.workshop.database.repositories;

import com.doityourself.workshop.database.entities.DiyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Diy User Repository
 */
@Repository
public interface DiyUserRepository extends CrudRepository<DiyUser, Long> {
  /**
   * Method to find user by username and password
   *
   * @param userName {@link String}
   * @param password {@link String}
   * @return {@link DiyUser}
   */
  DiyUser findByUserNameAndPassword(String userName, String password);

  /**
   * Method to find user by username
   *
   * @param userName {@link String}
   * @return {@link DiyUser}
   */
  DiyUser findFirstByUserName(String userName);

  /**
   * Method to find all users that do not match a specific username
   *
   * @param userName {@link String}
   * @return {@link List}&lt;{@link DiyUser}&gt;
   */
  List<DiyUser> findByUserNameNot(String userName);
}
