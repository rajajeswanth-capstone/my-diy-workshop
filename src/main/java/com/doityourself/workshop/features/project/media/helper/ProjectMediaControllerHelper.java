package com.doityourself.workshop.features.project.media.helper;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.enums.LocalResourceType;
import com.doityourself.workshop.common.enums.WebResourceType;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.springframework.ui.Model;

/**
 * Project Media Controller Helper
 */
public class ProjectMediaControllerHelper {
  /**
   * Method to set open local resource model details
   *
   * @param model {@link Model}
   * @param commandDTO {@link CommandDTO}
   * @param displaySequence {@link Integer}
   */
  public static void setOpenLocalResourceModelDetails(Model model, CommandDTO commandDTO, Integer displaySequence) {
    if (commandDTO == null) {
      return;
    }
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = (ProjectDetailLocalResourceRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE);
    if (projectDetailLocalResourceRepresentation != null) {
      ProjectMediaControllerHelper.setModelListingOperationResourceTypeSuccess(model, projectDetailLocalResourceRepresentation.getResourceType());
      ProjectMediaControllerHelper.setModelDisplaySequence(model, projectDetailLocalResourceRepresentation.getResourceType(), displaySequence);
    }
  }

  /**
   * Method to set local resource artifact
   *
   * @param model {@link Model}
   * @param resourceType {@link String}
   */
  public static void setLocalResourceArtifact(Model model, String resourceType) {
    LocalResourceType localResourceType = LocalResourceType.valueOf(resourceType);
    if (localResourceType == LocalResourceType.IMAGE) {
      model.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    } else if (localResourceType == LocalResourceType.VIDEO) {
      model.addAttribute(ModelConstants.MODEL_PROJECT_VIDEO_RESOURCE_ARTIFACT, true);
    } else if (localResourceType == LocalResourceType.DOCUMENT) {
      model.addAttribute(ModelConstants.MODEL_PROJECT_DOCUMENT_RESOURCE_ARTIFACT, true);
    } else if (localResourceType == LocalResourceType.OTHER) {
      model.addAttribute(ModelConstants.MODEL_PROJECT_OTHER_RESOURCE_ARTIFACT, true);
    }
  }

  /**
   * Method to set resource type success
   *
   * @param model {@link Model}
   * @param resourceType {@link String}
   */
  public static void setModelListingOperationResourceTypeSuccess(Model model, String resourceType) {
    model.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, LocalResourceType.valueOf(resourceType).getValue() + ModelConstants.MODEL_MEDIA_LISTING_OPERATION_SAVE_SUCCESS_SUFFIX);
  }

  /**
   * Method to set resource type success
   *
   * @param model {@link Model}
   */
  public static void setModelListingOperationWebResourceSuccess(Model model) {
    model.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue() + ModelConstants.MODEL_MEDIA_LISTING_OPERATION_SAVE_SUCCESS_SUFFIX);
  }

  /**
   * Method to set resource type errors
   *
   * @param model {@link Model}
   * @param resourceType {@link String}
   */
  public static void setModelListingOperationResourceTypeErrors(Model model, String resourceType) {
    model.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, LocalResourceType.valueOf(resourceType).getValue() + ModelConstants.MODEL_MEDIA_LISTING_OPERATION_SAVE_ERRORS_SUFFIX);
  }

  /**
   * Method to set resource type errors
   *
   * @param model {@link Model}
   */
  public static void setModelListingOperationWebResourceErrors(Model model) {
    model.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue() + ModelConstants.MODEL_MEDIA_LISTING_OPERATION_SAVE_ERRORS_SUFFIX);
  }

  /**
   * Method to set model display sequence
   *
   * @param model {@link Model}
   * @param resourceType {@link String}
   */
  public static void setModelDisplaySequence(Model model, String resourceType, Integer displaySequence) {
    LocalResourceType localResourceType = LocalResourceType.valueOf(resourceType);
    if (localResourceType == LocalResourceType.IMAGE) {
      model.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, displaySequence);
    } else if (localResourceType == LocalResourceType.VIDEO) {
      model.addAttribute(ModelConstants.MODEL_SAVE_VIDEO_DISPLAY_SEQUENCE, displaySequence);
    } else if (localResourceType == LocalResourceType.DOCUMENT) {
      model.addAttribute(ModelConstants.MODEL_SAVE_DOCUMENT_DISPLAY_SEQUENCE, displaySequence);
    } else if (localResourceType == LocalResourceType.OTHER) {
      model.addAttribute(ModelConstants.MODEL_SAVE_OTHER_DISPLAY_SEQUENCE, displaySequence);
    }
  }
}
