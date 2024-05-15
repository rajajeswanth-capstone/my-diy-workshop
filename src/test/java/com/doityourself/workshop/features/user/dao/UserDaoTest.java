package com.doityourself.workshop.features.user.dao;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.repositories.DiyUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoTest {
  @Test
  public void testSaveUser() {
    // Initialize
    DiyUser diyUser = DiyUser.builder().id(1L).build();
    List<DiyUser> diyUsers = new ArrayList<>();
    diyUsers.add(diyUser);

    DiyUserRepository mockDiyUserRepository = Mockito.mock(DiyUserRepository.class);

    UserDao userDao = new UserDao();
    userDao.diyUserRepository = mockDiyUserRepository;

    // Define Mocks
    Mockito.when(mockDiyUserRepository.findByUserNameNot("username")).thenReturn(diyUsers);

    // Execute
    Exception expectedException = null;
    List<DiyUser> result = null;
    try {
      result = userDao.getSharedUsers("username");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserRepository, Mockito.times(1)).findByUserNameNot("username");

    // Assertions
    assert expectedException == null;
    assert result == diyUsers;
  }

  @Test
  public void testFindByUserId() {
    // Initialize
    DiyUser diyUser = DiyUser.builder().id(1L).build();

    DiyUserRepository mockDiyUserRepository = Mockito.mock(DiyUserRepository.class);

    UserDao userDao = new UserDao();
    userDao.diyUserRepository = mockDiyUserRepository;

    // Define Mocks
    Mockito.when(mockDiyUserRepository.findById(1L)).thenReturn(Optional.of(diyUser));

    // Execute
    Exception expectedException = null;
    DiyUser result = null;
    try {
      result = userDao.findByUserId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserRepository, Mockito.times(1)).findById(1L);

    // Assertions
    assert expectedException == null;
    assert result == diyUser;
  }

  @Test
  public void testFindByUserIdNotFound() {
    // Initialize
    DiyUserRepository mockDiyUserRepository = Mockito.mock(DiyUserRepository.class);

    UserDao userDao = new UserDao();
    userDao.diyUserRepository = mockDiyUserRepository;

    // Define Mocks
    Mockito.when(mockDiyUserRepository.findById(1L)).thenReturn(Optional.empty());

    // Execute
    Exception expectedException = null;
    DiyUser result = null;
    try {
      result = userDao.findByUserId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserRepository, Mockito.times(1)).findById(1L);

    // Assertions
    assert expectedException == null;
    assert result == null;
  }
}
