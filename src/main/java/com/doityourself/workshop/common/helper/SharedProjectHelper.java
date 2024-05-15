package com.doityourself.workshop.common.helper;

import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Shared Project Helper
 */
public class SharedProjectHelper {
  /**
   * Method to set shared project indicator
   *
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   */
  @SuppressWarnings("unchecked")
  public static void setSharedProjectIndicator(HttpServletRequest request, Long projectId) {
    List<ProjectListingRepresentation> projects = (List<ProjectListingRepresentation>) request.getSession()
        .getAttribute(ModelConstants.MODEL_SHARED_PROJECTS);
    ProjectListingRepresentation projectListingRepresentation = Optional.ofNullable(projects)
        .flatMap(projectListingRepresentations -> projectListingRepresentations.stream()
            .filter(it -> Objects.equals(it.getId(), projectId))
            .findFirst()
        ).orElse(null);
    if (projectListingRepresentation != null) {
      request.getSession().setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, true);
    } else {
      request.getSession().setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    }
  }
}
