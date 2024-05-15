package com.doityourself.workshop.features.signup.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.signup.dao.SignupDao;
import com.doityourself.workshop.features.signup.exception.SignupFailedException;
import com.doityourself.workshop.features.signup.representation.SignupUserRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Signup Validations
 */
@Slf4j
@Component
public class SignupValidations {
  @Value("${signup.validation.failed}")
  String signupFailedErrorMessage;

  @Value("${signup.validation.userName.required}")
  String signupUsernameRequiredErrorMessage;

  @Value("${signup.validation.password.required}")
  String signupPasswordRequiredErrorMessage;

  @Value("${signup.validation.name.required}")
  String signupNameRequiredErrorMessage;

  @Value("${signup.validation.userName.exists}")
  String signupUserNameAlreadyExistsErrorMessage;

  @Value("${signup.validation.userName.fieldName}")
  String signupUsernameFieldName;

  @Value("${signup.validation.password.fieldName}")
  String signupPasswordFieldName;

  @Value("${signup.validation.name.fieldName}")
  String signupNameFieldName;

  @Autowired
  SignupDao signupDao;

  /**
   * Method to validate {@link SignupUserRepresentation}
   *
   * @param user {@link SignupUserRepresentation}
   */
  public void validateSignupRequest(SignupUserRepresentation user) {
    validateUserNameExists(user);

    Map<String, String> fieldMessages = new HashMap<>();
    validateUserName(user, fieldMessages);
    validatePassword(user, fieldMessages);
    validateName(user, fieldMessages);

    if (fieldMessages.size() > 0) {
      SignupFailedException exception = new SignupFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate entity
   *
   * @param diyUser {@link DiyUser}
   */
  public void validateDiyUserEntity(DiyUser diyUser) {
    List<String> messages = new ArrayList<>();
    validateNullDiyUser(diyUser, messages);

    if (messages.size() > 0) {
      SignupFailedException exception = new SignupFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyUser} entity
   *
   * @param user {@link DiyUser}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyUser(DiyUser user, List<String> messages) {
    if (Objects.isNull(user)) {
      messages.add(signupFailedErrorMessage);
    }
  }

  /**
   * Method to validate username
   *
   * @param user {@link SignupUserRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateUserName(SignupUserRepresentation user, Map<String, String> messages) {
    if (StringUtility.isEmpty(user.getUserName())) {
      messages.put(signupUsernameFieldName, signupUsernameRequiredErrorMessage);
    }
  }

  /**
   * Method to validate if username already exists
   *
   * @param user {@link SignupUserRepresentation}
   */
  private void validateUserNameExists(SignupUserRepresentation user) {
    if (signupDao.doesUserExist(user.getUserName())) {
      SignupFailedException exception = new SignupFailedException();
      exception.setMessages(Collections.singletonList(signupUserNameAlreadyExistsErrorMessage));
      throw exception;
    }
  }

  /**
   * Method to validate password
   *
   * @param user {@link SignupUserRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validatePassword(SignupUserRepresentation user, Map<String, String> messages) {
    if (StringUtility.isEmpty(user.getPassword())) {
      messages.put(signupPasswordFieldName, signupPasswordRequiredErrorMessage);
    }
  }

  /**
   * Method to validate name
   *
   * @param user {@link SignupUserRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateName(SignupUserRepresentation user, Map<String, String> messages) {
    if (StringUtility.isEmpty(user.getName())) {
      messages.put(signupNameFieldName, signupNameRequiredErrorMessage);
    }
  }
}
