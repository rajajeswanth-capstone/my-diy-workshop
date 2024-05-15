package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.exception.GetProjectLocalResourceFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Get Project Local Resource Validations
 */
@Component
public class GetProjectLocalResourceValidations {
  @Value("${project.localresource.validation.failed}")
  String projectLocalResourceFailedErrorMessage;

  @Value("${project.localresource.validation.id.required}")
  String projectLocalResourceIdRequiredErrorMessage;

  @Value("${project.localresource.validation.id.fieldName}")
  String projectLocalResourceIdFieldName;

  /**
   * Method to validate project local resource id
   *
   * @param id {@link Long}
   */
  public void validateGetProjectLocalResourceRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectLocalResourceId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      GetProjectLocalResourceFailedException exception = new GetProjectLocalResourceFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate entity
   *
   * @param diyProjectLocalResource {@link DiyProjectLocalResource}
   */
  public void validateDiyProjectLocalResourceEntity(DiyProjectLocalResource diyProjectLocalResource) {
    List<String> messages = new ArrayList<>();
    validateNullDiyProjectLocalResource(diyProjectLocalResource, messages);

    if (messages.size() > 0) {
      GetProjectLocalResourceFailedException exception = new GetProjectLocalResourceFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyProjectLocalResource} entity
   *
   * @param diyProjectLocalResource {@link DiyProjectLocalResource}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyProjectLocalResource(DiyProjectLocalResource diyProjectLocalResource, List<String> messages) {
    if (Objects.isNull(diyProjectLocalResource)) {
      messages.add(projectLocalResourceFailedErrorMessage);
    }
  }

  /**
   * Method to validate project local resource id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectLocalResourceId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectLocalResourceIdFieldName, projectLocalResourceIdRequiredErrorMessage);
    }
  }
}
