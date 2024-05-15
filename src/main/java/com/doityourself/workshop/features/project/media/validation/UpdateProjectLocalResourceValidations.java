package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.exception.UpdateProjectLocalResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Update Project Local Resource Validations
 */
@Component
public class UpdateProjectLocalResourceValidations {
  @Value("${project.update.localresource.validation.failed}")
  String projectUpdateLocalResourceFailedErrorMessage;

  @Value("${project.update.localresource.validation.title.required}")
  String projectUpdateLocalResourceTitleRequiredErrorMessage;

  @Value("${project.update.localresource.validation.title.fieldName}")
  String projectUpdateLocalResourceTitleFieldName;

  @Value("${project.update.localresource.validation.sequence.required}")
  String projectUpdateLocalResourceSequenceRequiredErrorMessage;

  @Value("${project.update.localresource.validation.sequence.fieldName}")
  String projectUpdateLocalResourceSequenceFieldName;

  @Value("${project.update.localresource.validation.id.required}")
  String projectUpdateLocalResourceIdRequiredErrorMessage;

  @Value("${project.update.localresource.validation.id.fieldName}")
  String projectUpdateLocalResourceIdFieldName;

  /**
   * Method to validate {@link ProjectDetailLocalResourceRepresentation}
   *
   * @param localResource {@link ProjectDetailLocalResourceRepresentation}
   */
  public void validateUpdateLocalResourceRequest(ProjectDetailLocalResourceRepresentation localResource) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateTitle(localResource, fieldMessages);
    validateSequence(localResource, fieldMessages);
    validateId(localResource, fieldMessages);

    if (fieldMessages.size() > 0) {
      UpdateProjectLocalResourceFailedException exception = new UpdateProjectLocalResourceFailedException();
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
      UpdateProjectLocalResourceFailedException exception = new UpdateProjectLocalResourceFailedException();
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
      messages.add(projectUpdateLocalResourceFailedErrorMessage);
    }
  }

  /**
   * Method to validate title
   *
   * @param localResource {@link ProjectDetailLocalResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateTitle(ProjectDetailLocalResourceRepresentation localResource, Map<String, String> messages) {
    if (StringUtility.isEmpty(localResource.getTitle())) {
      messages.put(projectUpdateLocalResourceTitleFieldName, projectUpdateLocalResourceTitleRequiredErrorMessage);
    }
  }

  /**
   * Method to validate sequence
   *
   * @param localResource {@link ProjectDetailLocalResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateSequence(ProjectDetailLocalResourceRepresentation localResource, Map<String, String> messages) {
    if (Objects.isNull(localResource.getLocalResourceSequence())) {
      messages.put(projectUpdateLocalResourceSequenceFieldName, projectUpdateLocalResourceSequenceRequiredErrorMessage);
    }
  }

  /**
   * Method to validate id
   *
   * @param localResource {@link ProjectDetailLocalResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateId(ProjectDetailLocalResourceRepresentation localResource, Map<String, String> messages) {
    if (Objects.isNull(localResource.getId())) {
      messages.put(projectUpdateLocalResourceIdFieldName, projectUpdateLocalResourceIdRequiredErrorMessage);
    }
  }
}
