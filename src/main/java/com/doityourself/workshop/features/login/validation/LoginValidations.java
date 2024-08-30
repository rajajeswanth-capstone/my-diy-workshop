package com.doityourself.workshop.features.login.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.exception.LoginFailedException;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Login Validations
 */
@Slf4j
@Component
public class LoginValidations {
  @Value("${login.validation.failed}")
  String loginFailedErrorMessage;

  @Value("${login.validation.userName.required}")
  String loginUsernameRequiredErrorMessage;

  @Value("${login.validation.password.required}")
  String loginPasswordRequiredErrorMessage;

  @Value("${login.validation.userName.fieldName}")
  String loginUsernameFieldName;

  @Value("${login.validation.password.fieldName}")
  String loginPasswordFieldName;

  @Autowired
  BasicPasswordEncryptor passwordEncryptor;

  /**
   * Method to validate {@link LoginUserRepresentation}
   *
   * @param user {@link LoginUserRepresentation}
   */
  public void validateLoginRequest(LoginUserRepresentation user) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateUserName(user, fieldMessages);
    validatePassword(user, fieldMessages);

    if (fieldMessages.size() > 0) {
      LoginFailedException exception = new LoginFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate login user entity
   *
   * @param diyUser {@link DiyUser}
   */
  public void validateDiyUserEntity(DiyUser diyUser, LoginUserRepresentation user) {
    List<String> messages = new ArrayList<>();
    validateNullDiyUser(diyUser, messages);
    validateDiyUserPassword(diyUser, user, messages);

    if (messages.size() > 0) {
      LoginFailedException exception = new LoginFailedException();
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
      messages.add(loginFailedErrorMessage);
    }
  }

  /**
   * Method to validate {@link DiyUser} entity
   *
   * @param user {@link DiyUser}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateDiyUserPassword(DiyUser diyUser, LoginUserRepresentation user, List<String> messages) {
    if (messages.size() > 0) {
      return;
    }
    if (!passwordEncryptor.checkPassword(user.getPassword(), diyUser.getPassword())) {
      messages.add(loginFailedErrorMessage);
    }
  }

  /**
   * Method to validate username
   *
   * @param user {@link LoginUserRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateUserName(LoginUserRepresentation user, Map<String, String> messages) {
    if (StringUtility.isEmpty(user.getUserName())) {
      messages.put(loginUsernameFieldName, loginUsernameRequiredErrorMessage);
    }
  }

  /**
   * Method to validate password
   *
   * @param user {@link LoginUserRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validatePassword(LoginUserRepresentation user, Map<String, String> messages) {
    if (StringUtility.isEmpty(user.getPassword())) {
      messages.put(loginPasswordFieldName, loginPasswordRequiredErrorMessage);
    }
  }
}
