package com.doityourself.workshop.features.user.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.user.validation.GetSharedUsersForProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetShareUsersForProjectRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetSharedUsersForProjectValidations mockGetSharedUsersForProjectValidations = Mockito.mock(GetSharedUsersForProjectValidations.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGGED_IN_USER, loggedInUserRepresentation).build();

    GetShareUsersForProjectRequestValidationCommand command = new GetShareUsersForProjectRequestValidationCommand();
    command.validations = mockGetSharedUsersForProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockGetSharedUsersForProjectValidations).validateCurrentUserName("username");

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetSharedUsersForProjectValidations, Mockito.times(1)).validateCurrentUserName("username");

    // Assertions
    assert expectedException == null;
  }
}
