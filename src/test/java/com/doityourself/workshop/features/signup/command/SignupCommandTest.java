package com.doityourself.workshop.features.signup.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.signup.dao.SignupDao;
import com.doityourself.workshop.features.signup.representation.SignupUserRepresentation;
import com.doityourself.workshop.features.signup.validation.SignupValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SignupCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    SignupDao mockSignupDao = Mockito.mock(SignupDao.class);

    ArgumentCaptor<DiyUser> diyUserCaptor = ArgumentCaptor.forClass(DiyUser.class);

    SignupUserRepresentation signupUserRepresentation = SignupUserRepresentation
        .builder().userName("username").password("password").name("name").build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGIN_USER, signupUserRepresentation).build();

    DiyUser responseDiyUser = DiyUser.builder().id(1L).build();

    SignupCommand command = new SignupCommand();
    command.signupDao = mockSignupDao;

    // Define Mocks
    Mockito.when(mockSignupDao.saveUser(Mockito.any())).thenReturn(responseDiyUser);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSignupDao, Mockito.times(1)).saveUser(diyUserCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert diyUserCaptor.getValue().getUserName().equals("username");
    assert diyUserCaptor.getValue().getPassword().equals("password");
    assert diyUserCaptor.getValue().getName().equals("name");
    assert commandDTO.get(EntityConstants.ENTITY_DIY_USER) == responseDiyUser;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SignupValidations mockSignupValidations = Mockito.mock(SignupValidations.class);

    DiyUser diyUser = DiyUser.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_USER, diyUser).build();

    SignupCommand command = new SignupCommand();
    command.signupValidations = mockSignupValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSignupValidations).validateDiyUserEntity(diyUser);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSignupValidations, Mockito.times(1)).validateDiyUserEntity(diyUser);

    // Assertions
    assert expectedException == null;
  }
}
