package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.exception.UpdateProjectWebResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Update Project Web Resource Validations
 */
@Component
public class UpdateProjectWebResourceValidations {
  @Value("${project.update.webresource.validation.failed}")
  String projectUpdateWebResourceFailedErrorMessage;

  @Value("${project.update.webresource.validation.title.required}")
  String projectUpdateWebResourceTitleRequiredErrorMessage;

  @Value("${project.update.webresource.validation.title.fieldName}")
  String projectUpdateWebResourceTitleFieldName;

  @Value("${project.update.webresource.validation.sequence.required}")
  String projectUpdateWebResourceSequenceRequiredErrorMessage;

  @Value("${project.update.webresource.validation.sequence.fieldName}")
  String projectUpdateWebResourceSequenceFieldName;

  @Value("${project.update.webresource.validation.id.required}")
  String projectUpdateWebResourceIdRequiredErrorMessage;

  @Value("${project.update.webresource.validation.id.fieldName}")
  String projectUpdateWebResourceIdFieldName;

  @Value("${project.update.webresource.validation.link.required}")
  String projectUpdateWebResourceLinkRequiredErrorMessage;

  @Value("${project.update.webresource.validation.link.fieldName}")
  String projectUpdateWebResourceLinkFieldName;

  /**
   * Method to validate {@link ProjectDetailWebResourceRepresentation}
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   */
  public void validateUpdateWebResourceRequest(ProjectDetailWebResourceRepresentation webResource) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateTitle(webResource, fieldMessages);
    validateSequence(webResource, fieldMessages);
    validateId(webResource, fieldMessages);
    validateLink(webResource, fieldMessages);

    if (fieldMessages.size() > 0) {
      UpdateProjectWebResourceFailedException exception = new UpdateProjectWebResourceFailedException();
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
    validateNullDiyProjectWebResource(diyProjectWebResource, messages);

    if (messages.size() > 0) {
      UpdateProjectWebResourceFailedException exception = new UpdateProjectWebResourceFailedException();
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
  private void validateNullDiyProjectWebResource(DiyProjectWebResource diyProjectWebResource, List<String> messages) {
    if (Objects.isNull(diyProjectWebResource)) {
      messages.add(projectUpdateWebResourceFailedErrorMessage);
    }
  }

  /**
   * Method to validate title
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateTitle(ProjectDetailWebResourceRepresentation webResource, Map<String, String> messages) {
    if (StringUtility.isEmpty(webResource.getTitle())) {
      messages.put(projectUpdateWebResourceTitleFieldName, projectUpdateWebResourceTitleRequiredErrorMessage);
    }
  }

  /**
   * Method to validate sequence
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateSequence(ProjectDetailWebResourceRepresentation webResource, Map<String, String> messages) {
    if (Objects.isNull(webResource.getWebResourceSequence())) {
      messages.put(projectUpdateWebResourceSequenceFieldName, projectUpdateWebResourceSequenceRequiredErrorMessage);
    }
  }

  /**
   * Method to validate id
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateId(ProjectDetailWebResourceRepresentation webResource, Map<String, String> messages) {
    if (Objects.isNull(webResource.getId())) {
      messages.put(projectUpdateWebResourceIdFieldName, projectUpdateWebResourceIdRequiredErrorMessage);
    }
  }

  /**
   * Method to validate link
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateLink(ProjectDetailWebResourceRepresentation webResource, Map<String, String> messages) {
    if (StringUtility.isEmpty(webResource.getLink())) {
      messages.put(projectUpdateWebResourceLinkFieldName, projectUpdateWebResourceLinkRequiredErrorMessage);
    }
  }
}
