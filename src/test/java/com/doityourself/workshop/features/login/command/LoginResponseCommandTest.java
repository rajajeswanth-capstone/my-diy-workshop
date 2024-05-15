package com.doityourself.workshop.features.login.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import org.junit.jupiter.api.Test;

public class LoginResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyUser diyUser = new DiyUser();
    diyUser.setUserName("username");
    diyUser.setName("name");
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_USER, diyUser).build();
    LoginResponseCommand loginResponseCommand = new LoginResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      loginResponseCommand.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert ((LoggedInUserRepresentation)commandDTO.get(ContextConstants.CONTEXT_LOGGED_IN_USER)).getUserName().equals("username");
    assert ((LoggedInUserRepresentation)commandDTO.get(ContextConstants.CONTEXT_LOGGED_IN_USER)).getName().equals("name");
  }
}
