package com.doityourself.workshop.features.user.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Get shared users for project validations
 */
@Component
public class GetSharedUsersForProjectValidations {
  @Value("${user.share.validation.username.required}")
  String userShareUsernameRequiredErrorMessage;

  @Value("${user.share.validation.username.fieldName}")
  String userShareIdFieldName;

  /**
   * Method to validate current username
   *
   * @param userName {@link String}
   */
  public void validateCurrentUserName(String userName) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateUsername(userName, fieldMessages);

    if (fieldMessages.size() > 0) {
      ProjectDeleteFailedException exception = new ProjectDeleteFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate current username
   *
   * @param userName {@link String}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateUsername(String userName, Map<String, String> messages) {
    if (StringUtility.isEmpty(userName)) {
      messages.put(userShareIdFieldName, userShareUsernameRequiredErrorMessage);
    }
  }
}
