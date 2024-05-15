package com.doityourself.workshop.features.project.share.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.features.project.share.exception.ProjectShareFailedException;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Save Project Validations
 */
@Component
public class ShareProjectValidations {
  @Value("${project.share.validation.failed}")
  String projectShareFailedErrorMessage;

  @Value("${project.share.validation.association.failed}")
  String projectShareAssociationFailedErrorMessage;

  @Value("${project.share.validation.projectId.required}")
  String projectShareProjectIdRequiredErrorMessage;

  @Value("${project.share.validation.projectId.fieldName}")
  String projectShareProjectIdFieldName;

  @Value("${project.share.validation.sharedUserId.required}")
  String projectShareUserIdRequiredErrorMessage;

  @Value("${project.share.validation.sharedUserId.fieldName}")
  String projectShareUserIdFieldName;

  /**
   * Method to validate {@link ShareProjectRepresentation}
   *
   * @param shareProject {@link ShareProjectRepresentation}
   */
  public void validateShareProjectRequest(ShareProjectRepresentation shareProject) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectId(shareProject, fieldMessages);
    validateSharedUserId(shareProject, fieldMessages);

    if (fieldMessages.size() > 0) {
      ProjectShareFailedException exception = new ProjectShareFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate project entity
   *
   * @param diyProject {@link DiyProject}
   */
  public void validateDiyProjectEntity(DiyProject diyProject) {
    List<String> messages = new ArrayList<>();
    validateNullDiyProject(diyProject, messages);

    if (messages.size() > 0) {
      ProjectShareFailedException exception = new ProjectShareFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate user entity
   *
   * @param diyUser {@link DiyUser}
   */
  public void validateSharedDiyUserEntity(DiyUser diyUser) {
    List<String> messages = new ArrayList<>();
    validateNullDiyUser(diyUser, messages);

    if (messages.size() > 0) {
      ProjectShareFailedException exception = new ProjectShareFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate entity
   *
   * @param diyUserProject {@link DiyUserProject}
   */
  public void validateDiyUserProjectAssociation(DiyUserProject diyUserProject) {
    List<String> messages = new ArrayList<>();
    validateNullDiyUserProject(diyUserProject, messages);

    if (messages.size() > 0) {
      ProjectShareFailedException exception = new ProjectShareFailedException();
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
      messages.add(projectShareFailedErrorMessage);
    }
  }

  /**
   * Method to validate {@link DiyUser} entity
   *
   * @param diyUser {@link DiyUser}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyUser(DiyUser diyUser, List<String> messages) {
    if (Objects.isNull(diyUser)) {
      messages.add(projectShareFailedErrorMessage);
    }
  }

  /**
   * Method to validate {@link DiyUserProject} entity
   *
   * @param diyUserProject {@link DiyUserProject}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyUserProject(DiyUserProject diyUserProject, List<String> messages) {
    if (Objects.isNull(diyUserProject)) {
      messages.add(projectShareAssociationFailedErrorMessage);
    }
  }

  /**
   * Method to validate {@link ShareProjectRepresentation}
   *
   * @param shareProject {@link ShareProjectRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectId(ShareProjectRepresentation shareProject, Map<String, String> messages) {
    if (Objects.isNull(shareProject.getProjectId())) {
      messages.put(projectShareProjectIdFieldName, projectShareProjectIdRequiredErrorMessage);
    }
  }

  /**
   * Method to validate sharedUserId
   *
   * @param shareProject {@link ShareProjectRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateSharedUserId(ShareProjectRepresentation shareProject, Map<String, String> messages) {
    if (Objects.isNull(shareProject.getSharedUserId())) {
      messages.put(projectShareUserIdFieldName, projectShareUserIdRequiredErrorMessage);
    }
  }
}
