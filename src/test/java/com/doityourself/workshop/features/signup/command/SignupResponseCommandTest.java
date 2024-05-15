package com.doityourself.workshop.features.signup.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import org.junit.jupiter.api.Test;

public class SignupResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyUser diyUser = DiyUser.builder().id(1L).name("name").userName("username").build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_USER, diyUser).build();

    SignupResponseCommand command = new SignupResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    LoggedInUserRepresentation loggedInUserRepresentation = (LoggedInUserRepresentation) commandDTO.get(ContextConstants.CONTEXT_LOGGED_IN_USER);
    assert loggedInUserRepresentation.getUserName().equals("username");
    assert loggedInUserRepresentation.getName().equals("name");
  }
}
