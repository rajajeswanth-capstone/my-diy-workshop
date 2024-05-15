package com.doityourself.workshop.features.project.listing.validation;

import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Save Project Validations
 */
@Component
public class SaveProjectValidations {
  @Value("${project.save.validation.failed}")
  private String projectSaveFailedErrorMessage;

  @Value("${project.save.validation.association.failed}")
  private String projectSaveAssociationFailedErrorMessage;

  @Value("${project.save.validation.title.required}")
  private String projectSaveTitleRequiredErrorMessage;

  @Value("${project.save.validation.title.fieldName}")
  private String projectSaveTitleFieldName;

  /**
   * Method to validate {@link ProjectListingRepresentation}
   *
   * @param project {@link ProjectListingRepresentation}
   */
  public void validateSaveRequest(ProjectListingRepresentation project) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateTitle(project, fieldMessages);

    if (fieldMessages.size() > 0) {
      ProjectSaveFailedException exception = new ProjectSaveFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate if project folder is created
   *
   * @param diyProject {@link DiyProject}
   */
  public void validateProjectFolderCreate(DiyProject diyProject) {
    List<String> messages = new ArrayList<>();
    boolean isCreated = FileUtility.isProjectFoldersCreated(diyProject.getId());
    if (!isCreated) {
      messages.add(projectSaveFailedErrorMessage);

      ProjectSaveFailedException exception = new ProjectSaveFailedException();
      exception.setMessages(messages);
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
      ProjectSaveFailedException exception = new ProjectSaveFailedException();
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
      ProjectSaveFailedException exception = new ProjectSaveFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyUser} entity
   *
   * @param diyProject {@link DiyProject}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyProject(DiyProject diyProject, List<String> messages) {
    if (Objects.isNull(diyProject)) {
      messages.add(projectSaveFailedErrorMessage);
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
      messages.add(projectSaveAssociationFailedErrorMessage);
    }
  }

  /**
   * Method to validate title
   *
   * @param project {@link ProjectListingRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateTitle(ProjectListingRepresentation project, Map<String, String> messages) {
    if (StringUtility.isEmpty(project.getTitle())) {
      messages.put(projectSaveTitleFieldName, projectSaveTitleRequiredErrorMessage);
    }
  }
}
