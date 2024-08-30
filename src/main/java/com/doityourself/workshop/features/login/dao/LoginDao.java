package com.doityourself.workshop.features.login.dao;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.repositories.DiyUserRepository;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Login DAO
 */
@Repository
public class LoginDao {
  @Autowired
  DiyUserRepository repository;

  /**
   * Find user by username and password
   *
   * @param user {@link LoginUserRepresentation}
   * @return {@link DiyUser}
   */
  public DiyUser findMatchingUser(LoginUserRepresentation user) {
    return repository.findFirstByUserName(user.getUserName());
  }

  /**
   * Find logged in user by username
   *
   * @param user {@link LoginUserRepresentation}
   * @return {@link DiyUser}
   */
  public DiyUser findMatchingLoggedInUser(LoggedInUserRepresentation user) {
    return repository.findFirstByUserName(user.getUserName());
  }
}
