package com.doityourself.workshop.features.login.validation;

import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.exception.LoginFailedException;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import org.junit.jupiter.api.Test;

public class LoginValidationsTest {
  @Test
  public void testValidateLoginRequest() {
    // Initialize
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("username");
    loginUserRepresentation.setPassword("password");

    LoginValidations loginValidations = new LoginValidations();
    loginValidations.loginFailedErrorMessage = "Login Failed";
    loginValidations.loginUsernameRequiredErrorMessage = "Username required";
    loginValidations.loginPasswordRequiredErrorMessage = "Password required";
    loginValidations.loginUsernameFieldName = "username";
    loginValidations.loginPasswordFieldName = "password";

    // Execute
    Exception expectedException = null;
    try {
      loginValidations.validateLoginRequest(loginUserRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateLoginRequestUsernameRequired() {
    // Initialize
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setPassword("password");

    LoginValidations loginValidations = new LoginValidations();
    loginValidations.loginFailedErrorMessage = "Login Failed";
    loginValidations.loginUsernameRequiredErrorMessage = "Username required";
    loginValidations.loginPasswordRequiredErrorMessage = "Password required";
    loginValidations.loginUsernameFieldName = "username";
    loginValidations.loginPasswordFieldName = "password";

    // Execute
    Exception expectedException = null;
    try {
      loginValidations.validateLoginRequest(loginUserRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((LoginFailedException)expectedException).getFieldMessages().get("username").equals("Username required");
  }

  @Test
  public void testValidateLoginRequestPasswordRequired() {
    // Initialize
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("username");

    LoginValidations loginValidations = new LoginValidations();
    loginValidations.loginFailedErrorMessage = "Login Failed";
    loginValidations.loginUsernameRequiredErrorMessage = "Username required";
    loginValidations.loginPasswordRequiredErrorMessage = "Password required";
    loginValidations.loginUsernameFieldName = "username";
    loginValidations.loginPasswordFieldName = "password";

    // Execute
    Exception expectedException = null;
    try {
      loginValidations.validateLoginRequest(loginUserRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((LoginFailedException)expectedException).getFieldMessages().get("password").equals("Password required");
  }

  @Test
  public void testValidateDiyUserEntity() {
    // Initialize
    DiyUser diyUser = new DiyUser();
    diyUser.setUserName("username");
    diyUser.setPassword("password");

    LoginValidations loginValidations = new LoginValidations();
    loginValidations.loginFailedErrorMessage = "Login Failed";
    loginValidations.loginUsernameRequiredErrorMessage = "Username required";
    loginValidations.loginPasswordRequiredErrorMessage = "Password required";
    loginValidations.loginUsernameFieldName = "username";
    loginValidations.loginPasswordFieldName = "password";

    // Execute
    Exception expectedException = null;
    try {
      loginValidations.validateDiyUserEntity(diyUser);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyUserEntityNull() {
    // Initialize
    LoginValidations loginValidations = new LoginValidations();
    loginValidations.loginFailedErrorMessage = "Login Failed";
    loginValidations.loginUsernameRequiredErrorMessage = "Username required";
    loginValidations.loginPasswordRequiredErrorMessage = "Password required";
    loginValidations.loginUsernameFieldName = "username";
    loginValidations.loginPasswordFieldName = "password";

    // Execute
    Exception expectedException = null;
    try {
      loginValidations.validateDiyUserEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((LoginFailedException) expectedException).getMessages().get(0).equals("Login Failed");
  }
}
