package com.doityourself.workshop.features.login.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.dao.LoginDao;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import com.doityourself.workshop.features.login.validation.LoginValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    LoginDao mockLoginDao = Mockito.mock(LoginDao.class);
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("test");
    DiyUser diyUser = new DiyUser();
    diyUser.setId(1L);
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGIN_USER, loginUserRepresentation).build();
    LoginCommand loginCommand = new LoginCommand();
    loginCommand.loginDao = mockLoginDao;

    // Define Mocks
    Mockito.when(mockLoginDao.findMatchingUser(loginUserRepresentation)).thenReturn(diyUser);

    // Execute
    Exception expectedException = null;
    try {
      loginCommand.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockLoginDao, Mockito.times(1)).findMatchingUser(loginUserRepresentation);

    // Assertions
    assert expectedException == null;
    assert ((DiyUser) commandDTO.get(EntityConstants.ENTITY_DIY_USER)).getId() == 1L;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    LoginValidations mockLoginValidations = Mockito.mock(LoginValidations.class);
    DiyUser diyUser = new DiyUser();
    diyUser.setId(1L);
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("test");
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_USER, diyUser)
        .add(ContextConstants.CONTEXT_LOGIN_USER, loginUserRepresentation)
        .build();
    LoginCommand loginCommand = new LoginCommand();
    loginCommand.loginValidations = mockLoginValidations;

    // Define Mocks
    Mockito.doNothing().when(mockLoginValidations).validateDiyUserEntity(diyUser, loginUserRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      loginCommand.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockLoginValidations, Mockito.times(1)).validateDiyUserEntity(diyUser, loginUserRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
