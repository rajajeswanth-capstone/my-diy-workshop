package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.exception.GetProjectDetailFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Get Project Detail Validations
 */
@Component
public class GetProjectDetailValidations {
  @Value("${project.detail.validation.failed}")
  String projectDetailFailedErrorMessage;

  @Value("${project.detail.validation.id.required}")
  String projectDetailIdRequiredErrorMessage;

  @Value("${project.detail.validation.id.fieldName}")
  String projectDetailTitleFieldName;

  /**
   * Method to validate project id
   *
   * @param id {@link Long}
   */
  public void validateGetProjectDetailRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      GetProjectDetailFailedException exception = new GetProjectDetailFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate entity
   *
   * @param diyProject {@link DiyProject}
   */
  public void validateDiyProjectEntity(DiyProject diyProject) {
    List<String> messages = new ArrayList<>();
    validateNullDiyProject(diyProject, messages);

    if (messages.size() > 0) {
      GetProjectDetailFailedException exception = new GetProjectDetailFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyProject} entity
   *
   * @param diyProject {@link DiyProject}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyProject(DiyProject diyProject, List<String> messages) {
    if (Objects.isNull(diyProject)) {
      messages.add(projectDetailFailedErrorMessage);
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
      messages.put(projectDetailTitleFieldName, projectDetailIdRequiredErrorMessage);
    }
  }
}
