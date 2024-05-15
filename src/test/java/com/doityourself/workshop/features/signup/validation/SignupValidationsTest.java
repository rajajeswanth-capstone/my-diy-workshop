package com.doityourself.workshop.features.signup.validation;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.signup.dao.SignupDao;
import com.doityourself.workshop.features.signup.exception.SignupFailedException;
import com.doityourself.workshop.features.signup.representation.SignupUserRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SignupValidationsTest {
  @Test
  public void testValidateSignupRequest() {
    // Initialize
    SignupDao mockSignupDao = Mockito.mock(SignupDao.class);

    SignupValidations signupValidations = new SignupValidations();
    signupValidations.signupDao = mockSignupDao;

    signupValidations.signupFailedErrorMessage = "message1";
    signupValidations.signupUserNameAlreadyExistsErrorMessage = "message2";
    signupValidations.signupUsernameRequiredErrorMessage = "field1message1";
    signupValidations.signupUsernameFieldName = "field1";
    signupValidations.signupPasswordRequiredErrorMessage = "field2message1";
    signupValidations.signupPasswordFieldName = "field2";
    signupValidations.signupNameRequiredErrorMessage = "field3message1";
    signupValidations.signupNameFieldName = "field3";

    // Initialize Mocks
    Mockito.when(mockSignupDao.doesUserExist("username")).thenReturn(false);

    // Execute
    Exception expectedException = null;
    try {
      signupValidations.validateSignupRequest(
          SignupUserRepresentation.builder().userName("username").password("password").name("name").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateSignupRequestRequired() {
    // Initialize
    SignupDao mockSignupDao = Mockito.mock(SignupDao.class);

    SignupValidations signupValidations = new SignupValidations();
    signupValidations.signupDao = mockSignupDao;

    signupValidations.signupFailedErrorMessage = "message1";
    signupValidations.signupUserNameAlreadyExistsErrorMessage = "message2";
    signupValidations.signupUsernameRequiredErrorMessage = "field1message1";
    signupValidations.signupUsernameFieldName = "field1";
    signupValidations.signupPasswordRequiredErrorMessage = "field2message1";
    signupValidations.signupPasswordFieldName = "field2";
    signupValidations.signupNameRequiredErrorMessage = "field3message1";
    signupValidations.signupNameFieldName = "field3";

    // Initialize Mocks
    Mockito.when(mockSignupDao.doesUserExist("username")).thenReturn(false);

    // Execute
    Exception expectedException = null;
    try {
      signupValidations.validateSignupRequest(
          SignupUserRepresentation.builder().password("password").name("name").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SignupFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");

    expectedException = null;
    try {
      signupValidations.validateSignupRequest(
          SignupUserRepresentation.builder().userName("username").name("name").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SignupFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");

    expectedException = null;
    try {
      signupValidations.validateSignupRequest(
          SignupUserRepresentation.builder().userName("username").password("password").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SignupFailedException) expectedException).getFieldMessages().get("field3").equals("field3message1");
  }

  @Test
  public void testValidateSignupRequestUserExists() {
    // Initialize
    SignupDao mockSignupDao = Mockito.mock(SignupDao.class);

    SignupValidations signupValidations = new SignupValidations();
    signupValidations.signupDao = mockSignupDao;

    signupValidations.signupFailedErrorMessage = "message1";
    signupValidations.signupUserNameAlreadyExistsErrorMessage = "message2";
    signupValidations.signupUsernameRequiredErrorMessage = "field1message1";
    signupValidations.signupUsernameFieldName = "field1";
    signupValidations.signupPasswordRequiredErrorMessage = "field2message1";
    signupValidations.signupPasswordFieldName = "field2";
    signupValidations.signupNameRequiredErrorMessage = "field3message1";
    signupValidations.signupNameFieldName = "field3";

    // Initialize Mocks
    Mockito.when(mockSignupDao.doesUserExist("username")).thenReturn(true);

    // Execute
    Exception expectedException = null;
    try {
      signupValidations.validateSignupRequest(
          SignupUserRepresentation.builder().userName("username").password("password").name("name").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SignupFailedException) expectedException).getMessages().get(0).equals("message2");
  }

  @Test
  public void testValidateDiyUserEntity() {
    // Initialize
    SignupDao mockSignupDao = Mockito.mock(SignupDao.class);

    SignupValidations signupValidations = new SignupValidations();
    signupValidations.signupDao = mockSignupDao;

    signupValidations.signupFailedErrorMessage = "message1";
    signupValidations.signupUserNameAlreadyExistsErrorMessage = "message2";
    signupValidations.signupUsernameRequiredErrorMessage = "field1message1";
    signupValidations.signupUsernameFieldName = "field1";
    signupValidations.signupPasswordRequiredErrorMessage = "field2message1";
    signupValidations.signupPasswordFieldName = "field2";
    signupValidations.signupNameRequiredErrorMessage = "field3message1";
    signupValidations.signupNameFieldName = "field3";

    // Initialize Mocks
    Mockito.when(mockSignupDao.doesUserExist("username")).thenReturn(false);

    // Execute
    Exception expectedException = null;
    try {
      signupValidations.validateDiyUserEntity(
          DiyUser.builder().userName("username").password("password").name("name").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyUserEntityRequired() {
    // Initialize
    SignupDao mockSignupDao = Mockito.mock(SignupDao.class);

    SignupValidations signupValidations = new SignupValidations();
    signupValidations.signupDao = mockSignupDao;

    signupValidations.signupFailedErrorMessage = "message1";
    signupValidations.signupUserNameAlreadyExistsErrorMessage = "message2";
    signupValidations.signupUsernameRequiredErrorMessage = "field1message1";
    signupValidations.signupUsernameFieldName = "field1";
    signupValidations.signupPasswordRequiredErrorMessage = "field2message1";
    signupValidations.signupPasswordFieldName = "field2";
    signupValidations.signupNameRequiredErrorMessage = "field3message1";
    signupValidations.signupNameFieldName = "field3";

    // Initialize Mocks
    Mockito.when(mockSignupDao.doesUserExist("username")).thenReturn(false);

    // Execute
    Exception expectedException = null;
    try {
      signupValidations.validateDiyUserEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SignupFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
