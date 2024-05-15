package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Save Project Description Validations
 */
@Component
public class SaveProjectDescriptionValidations {
  @Value("${project.save.description.validation.failed}")
  String projectSaveDescriptionFailedErrorMessage;

  /**
   * Method to validate entity
   *
   * @param diyProject {@link DiyProject}
   * @param projectDetailRepresentation {@link ProjectDetailRepresentation}
   */
  public void validateDiyProjectDescription(DiyProject diyProject, ProjectDetailRepresentation projectDetailRepresentation) {
    List<String> messages = new ArrayList<>();
    validateProjectDescriptionUpdate(diyProject, projectDetailRepresentation, messages);

    if (messages.size() > 0) {
      ProjectSaveFailedException exception = new ProjectSaveFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyProjectInstruction} entity
   *
   * @param diyProject {@link DiyProject}
   * @param projectDetailRepresentation {@link ProjectDetailRepresentation}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateProjectDescriptionUpdate(DiyProject diyProject, ProjectDetailRepresentation projectDetailRepresentation, List<String> messages) {
    if (!(!Objects.isNull(diyProject) && !Objects.isNull(projectDetailRepresentation) &&
        ((diyProject.getDescription() == null && projectDetailRepresentation.getDescription() == null) ||
            (diyProject.getDescription().equals(projectDetailRepresentation.getDescription()))))) {
      messages.add(projectSaveDescriptionFailedErrorMessage);
    }
  }
}
