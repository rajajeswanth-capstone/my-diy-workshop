package com.doityourself.workshop.features.signup.dao;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.repositories.DiyUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SignupDaoTest {
  @Test
  public void testSaveUser() {
    // Initialize
    DiyUser diyUser = DiyUser.builder().id(1L).build();

    DiyUserRepository mockDiyUserRepository = Mockito.mock(DiyUserRepository.class);

    SignupDao signupDao = new SignupDao();
    signupDao.repository = mockDiyUserRepository;

    // Define Mocks
    Mockito.when(mockDiyUserRepository.save(diyUser)).thenReturn(diyUser);

    // Execute
    Exception expectedException = null;
    DiyUser result = null;
    try {
      result = signupDao.saveUser(diyUser);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserRepository, Mockito.times(1)).save(diyUser);

    // Assertions
    assert expectedException == null;
    assert result.getId() == 1L;
  }

  @Test
  public void testDoesUserExist() {
    // Initialize
    DiyUser diyUser = DiyUser.builder().id(1L).build();

    DiyUserRepository mockDiyUserRepository = Mockito.mock(DiyUserRepository.class);

    SignupDao signupDao = new SignupDao();
    signupDao.repository = mockDiyUserRepository;

    // Define Mocks
    Mockito.when(mockDiyUserRepository.findFirstByUserName("username")).thenReturn(diyUser);

    // Execute
    Exception expectedException = null;
    boolean result = false;
    try {
      result = signupDao.doesUserExist("username");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserRepository, Mockito.times(1)).findFirstByUserName("username");

    // Assertions
    assert expectedException == null;
    assert result;
  }
}
