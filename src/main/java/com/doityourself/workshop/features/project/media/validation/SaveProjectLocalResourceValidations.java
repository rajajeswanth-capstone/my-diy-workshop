package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.exception.SaveProjectLocalResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Save Project Validations
 */
@Component
public class SaveProjectLocalResourceValidations {
  @Value("${project.save.localresource.validation.failed}")
  String projectSaveLocalResourceFailedErrorMessage;

  @Value("${project.save.localresource.validation.title.required}")
  String projectSaveLocalResourceTitleRequiredErrorMessage;

  @Value("${project.save.localresource.validation.title.fieldName}")
  String projectSaveLocalResourceTitleFieldName;

  @Value("${project.save.localresource.validation.sequence.required}")
  String projectSaveLocalResourceSequenceRequiredErrorMessage;

  @Value("${project.save.localresource.validation.sequence.fieldName}")
  String projectSaveLocalResourceSequenceFieldName;

  /**
   * Method to validate {@link ProjectDetailLocalResourceRepresentation}
   *
   * @param image {@link ProjectDetailLocalResourceRepresentation}
   */
  public void validateSaveLocalResourceRequest(ProjectDetailLocalResourceRepresentation image) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateTitle(image, fieldMessages);
    validateSequence(image, fieldMessages);

    if (fieldMessages.size() > 0) {
      SaveProjectLocalResourceFailedException exception = new SaveProjectLocalResourceFailedException();
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
      SaveProjectLocalResourceFailedException exception = new SaveProjectLocalResourceFailedException();
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
      messages.add(projectSaveLocalResourceFailedErrorMessage);
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
      messages.put(projectSaveLocalResourceTitleFieldName, projectSaveLocalResourceTitleRequiredErrorMessage);
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
      messages.put(projectSaveLocalResourceSequenceFieldName, projectSaveLocalResourceSequenceRequiredErrorMessage);
    }
  }
}
