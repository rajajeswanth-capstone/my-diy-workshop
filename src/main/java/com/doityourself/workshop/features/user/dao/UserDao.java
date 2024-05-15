package com.doityourself.workshop.features.user.dao;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.repositories.DiyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User DAO
 */
@Repository
public class UserDao {
  @Autowired
  DiyUserRepository diyUserRepository;

  /**
   * Method to find all shared users except a specific username
   *
   * @param userName {@link String}
   * @return {@link List}&lt;{@link DiyUser}&gt;
   */
  public List<DiyUser> getSharedUsers(String userName) {
    return diyUserRepository.findByUserNameNot(userName);
  }

  /**
   * Method to find user by id
   *
   * @param id {@link Long}
   * @return {@link DiyUser}
   */
  public DiyUser findByUserId(Long id) {
    return diyUserRepository.findById(id)
        .orElse(null);
  }
}
