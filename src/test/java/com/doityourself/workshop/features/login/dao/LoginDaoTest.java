package com.doityourself.workshop.features.login.dao;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.repositories.DiyUserRepository;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginDaoTest {
  @Test
  public void testFindMatchingUser() {
    // Initialize
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("username");
    loginUserRepresentation.setPassword("password");

    DiyUser responseDiyUser = new DiyUser();
    responseDiyUser.setUserName("responseusername");
    responseDiyUser.setPassword("responsepassword");

    DiyUserRepository mockDiyUserRepository = Mockito.mock(DiyUserRepository.class);

    LoginDao loginDao = new LoginDao();
    loginDao.repository = mockDiyUserRepository;

    // Define Mocks
    Mockito.when(mockDiyUserRepository.findFirstByUserName("username")).thenReturn(responseDiyUser);

    // Execute
    Exception expectedException = null;
    DiyUser result = null;
    try {
      result = loginDao.findMatchingUser(loginUserRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserRepository, Mockito.times(1)).findFirstByUserName("username");

    // Assertions
    assert expectedException == null;
    assert result.getUserName().equals("responseusername");
    assert result.getPassword().equals("responsepassword");
  }

  @Test
  public void testFindMatchingLoggedInUser() {
    // Initialize
    LoggedInUserRepresentation loginUserRepresentation = LoggedInUserRepresentation
        .builder().userName("username").name("name").build();

    DiyUser responseDiyUser = new DiyUser();
    responseDiyUser.setUserName("responseusername");
    responseDiyUser.setPassword("responsepassword");

    DiyUserRepository mockDiyUserRepository = Mockito.mock(DiyUserRepository.class);

    LoginDao loginDao = new LoginDao();
    loginDao.repository = mockDiyUserRepository;

    // Define Mocks
    Mockito.when(mockDiyUserRepository.findFirstByUserName("username")).thenReturn(responseDiyUser);

    // Execute
    Exception expectedException = null;
    DiyUser result = null;
    try {
      result = loginDao.findMatchingLoggedInUser(loginUserRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserRepository, Mockito.times(1)).findFirstByUserName("username");

    // Assertions
    assert expectedException == null;
    assert result.getUserName().equals("responseusername");
    assert result.getPassword().equals("responsepassword");
  }
}
