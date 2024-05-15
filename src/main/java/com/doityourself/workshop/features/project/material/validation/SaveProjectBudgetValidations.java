package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Save Project Budget Validations
 */
@Component
public class SaveProjectBudgetValidations {
  @Value("${project.save.budget.validation.failed}")
  String projectSaveBudgetFailedErrorMessage;

  /**
   * Method to validate entity
   *
   * @param diyProject {@link DiyProject}
   * @param projectDetailRepresentation {@link ProjectDetailRepresentation}
   */
  public void validateDiyProjectBudget(DiyProject diyProject, ProjectDetailRepresentation projectDetailRepresentation) {
    List<String> messages = new ArrayList<>();
    validateProjectBudgetUpdate(diyProject, projectDetailRepresentation, messages);

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
  private void validateProjectBudgetUpdate(DiyProject diyProject, ProjectDetailRepresentation projectDetailRepresentation, List<String> messages) {
    if (!(!Objects.isNull(diyProject) && !Objects.isNull(projectDetailRepresentation) &&
        ((diyProject.getBudget() == null && projectDetailRepresentation.getBudget() == null) ||
            (Objects.equals(diyProject.getBudget(), projectDetailRepresentation.getBudget()))))) {
      messages.add(projectSaveBudgetFailedErrorMessage);
    }
  }
}
