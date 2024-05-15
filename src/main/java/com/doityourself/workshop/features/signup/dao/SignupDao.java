package com.doityourself.workshop.features.signup.dao;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.repositories.DiyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Signup DAO
 */
@Repository
public class SignupDao {
  @Autowired
  DiyUserRepository repository;

  /**
   * Save user
   *
   * @param user {@link DiyUser}
   * @return {@link DiyUser}
   */
  public DiyUser saveUser(DiyUser user) {
    return repository.save(user);
  }

  /**
   * Method to check if user exists
   *
   * @param userName {@link String}
   * @return boolean
   */
  public boolean doesUserExist(String userName) {
    return repository.findFirstByUserName(userName) != null;
  }
}
