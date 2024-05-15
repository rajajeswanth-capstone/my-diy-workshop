package com.doityourself.workshop.features.project.listing.validation;

import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Delete Project Validations
 */
@Component
public class DeleteProjectValidations {
  @Value("${project.delete.validation.failed}")
  String projectDeleteFailedErrorMessage;

  @Value("${project.delete.validation.id.required}")
  String projectDeleteIdRequiredErrorMessage;

  @Value("${project.delete.validation.id.fieldName}")
  String projectDeleteTitleFieldName;

  /**
   * Method to validate delete project request
   *
   * @param id {@link Long}
   */
  public void validateDeleteRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      ProjectDeleteFailedException exception = new ProjectDeleteFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate if project folder is deleted
   *
   * @param projectId {@link Long}
   */
  public void validateProjectFolderDelete(Long projectId) {
    List<String> messages = new ArrayList<>();
    boolean isDeleted = FileUtility.isProjectFolderDeleted(projectId);
    if (!isDeleted) {
      messages.add(projectDeleteFailedErrorMessage);

      ProjectDeleteFailedException exception = new ProjectDeleteFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate project id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectDeleteTitleFieldName, projectDeleteIdRequiredErrorMessage);
    }
  }
}
