package com.doityourself.workshop.features.user.validation;

import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import org.junit.jupiter.api.Test;

public class GetSharedUsersForProjectValidationsTest {
  @Test
  public void testValidateCurrentUserName() {
    // Initialize
    GetSharedUsersForProjectValidations getSharedUsersForProjectValidations = new GetSharedUsersForProjectValidations();

    getSharedUsersForProjectValidations.userShareUsernameRequiredErrorMessage = "field1message1";
    getSharedUsersForProjectValidations.userShareIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getSharedUsersForProjectValidations.validateCurrentUserName("username");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateCurrentUserNameRequired() {
    // Initialize
    GetSharedUsersForProjectValidations getSharedUsersForProjectValidations = new GetSharedUsersForProjectValidations();

    getSharedUsersForProjectValidations.userShareUsernameRequiredErrorMessage = "field1message1";
    getSharedUsersForProjectValidations.userShareIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getSharedUsersForProjectValidations.validateCurrentUserName(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((ProjectDeleteFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }
}
