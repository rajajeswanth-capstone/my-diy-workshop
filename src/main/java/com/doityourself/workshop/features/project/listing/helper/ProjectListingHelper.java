package com.doityourself.workshop.features.project.listing.helper;

import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import org.springframework.ui.Model;

/**
 * Project Listing helper
 */
public class ProjectListingHelper {
  /**
   * Method to set operation
   *
   * @param model {@link Model}
   * @param projectListingRepresentation {@link ProjectListingRepresentation}
   */
  public static void setOperation(Model model, ProjectListingRepresentation projectListingRepresentation) {
    if (projectListingRepresentation.getId() != null) {
      model.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_UPDATE);
    } else {
      model.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SAVE);
    }
  }
}
