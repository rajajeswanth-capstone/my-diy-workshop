package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.exception.SaveProjectWebResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Save Project Web Resource Validations
 */
@Component
public class SaveProjectWebResourceValidations {
  @Value("${project.save.webresource.validation.failed}")
  String projectSaveWebResourceFailedErrorMessage;

  @Value("${project.save.webresource.validation.title.required}")
  String projectSaveWebResourceTitleRequiredErrorMessage;

  @Value("${project.save.webresource.validation.title.fieldName}")
  String projectSaveWebResourceTitleFieldName;

  @Value("${project.save.webresource.validation.sequence.required}")
  String projectSaveWebResourceSequenceRequiredErrorMessage;

  @Value("${project.save.webresource.validation.sequence.fieldName}")
  String projectSaveWebResourceSequenceFieldName;

  @Value("${project.save.webresource.validation.link.required}")
  String projectSaveWebResourceLinkRequiredErrorMessage;

  @Value("${project.save.webresource.validation.link.fieldName}")
  String projectSaveWebResourceLinkFieldName;

  /**
   * Method to validate {@link ProjectDetailWebResourceRepresentation}
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   */
  public void validateSaveWebResourceRequest(ProjectDetailWebResourceRepresentation webResource) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateTitle(webResource, fieldMessages);
    validateSequence(webResource, fieldMessages);
    validateLink(webResource, fieldMessages);

    if (fieldMessages.size() > 0) {
      SaveProjectWebResourceFailedException exception = new SaveProjectWebResourceFailedException();
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
      SaveProjectWebResourceFailedException exception = new SaveProjectWebResourceFailedException();
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
      messages.add(projectSaveWebResourceFailedErrorMessage);
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
      messages.put(projectSaveWebResourceTitleFieldName, projectSaveWebResourceTitleRequiredErrorMessage);
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
      messages.put(projectSaveWebResourceSequenceFieldName, projectSaveWebResourceSequenceRequiredErrorMessage);
    }
  }

  /**
   * Method to validate link
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateLink(ProjectDetailWebResourceRepresentation webResource, Map<String, String> messages) {
    if (Objects.isNull(webResource.getLink())) {
      messages.put(projectSaveWebResourceLinkFieldName, projectSaveWebResourceLinkRequiredErrorMessage);
    }
  }
}
