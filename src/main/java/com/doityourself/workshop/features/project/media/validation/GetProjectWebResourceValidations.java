package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.exception.GetProjectWebResourceFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Get Project Web Resource Validations
 */
@Component
public class GetProjectWebResourceValidations {
  @Value("${project.webresource.validation.failed}")
  String projectWebResourceFailedErrorMessage;

  @Value("${project.webresource.validation.id.required}")
  String projectWebResourceIdRequiredErrorMessage;

  @Value("${project.webresource.validation.id.fieldName}")
  String projectWebResourceIdFieldName;

  /**
   * Method to validate project web resource id
   *
   * @param id {@link Long}
   */
  public void validateGetProjectWebResourceRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectWebResourceId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      GetProjectWebResourceFailedException exception = new GetProjectWebResourceFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate entity
   *
   * @param diyProjectWebResource {@link DiyProjectWebResource}
   */
  public void validateDiyProjectWebResourceEntity(DiyProjectWebResource diyProjectWebResource) {
    List<String> messages = new ArrayList<>();
    validateNullDiyProjectImage(diyProjectWebResource, messages);

    if (messages.size() > 0) {
      GetProjectWebResourceFailedException exception = new GetProjectWebResourceFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyProjectWebResource} entity
   *
   * @param diyProjectWebResource {@link DiyProjectWebResource}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyProjectImage(DiyProjectWebResource diyProjectWebResource, List<String> messages) {
    if (Objects.isNull(diyProjectWebResource)) {
      messages.add(projectWebResourceFailedErrorMessage);
    }
  }

  /**
   * Method to validate project web resource id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectWebResourceId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectWebResourceIdFieldName, projectWebResourceIdRequiredErrorMessage);
    }
  }
}
