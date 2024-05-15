package com.doityourself.workshop.features.project.media.helper;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

public class ProjectMediaControllerHelperTest {
  @Test
  public void testSetOpenLocalResourceModelDetails() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE")
        .build();

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setOpenLocalResourceModelDetails(mockModel, commandDTO, 1);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetOpenLocalResourceModelDetailsNullCommandDTO() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE")
        .build();

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setOpenLocalResourceModelDetails(mockModel, null, 1);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success");
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetLocalResourceArtifact() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setLocalResourceArtifact(mockModel, "IMAGE");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;

    try {
      ProjectMediaControllerHelper.setLocalResourceArtifact(mockModel, "VIDEO");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_VIDEO_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;

    try {
      ProjectMediaControllerHelper.setLocalResourceArtifact(mockModel, "DOCUMENT");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DOCUMENT_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;

    try {
      ProjectMediaControllerHelper.setLocalResourceArtifact(mockModel, "OTHER");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_OTHER_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetModelListingOperationResourceTypeSuccess() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setModelListingOperationResourceTypeSuccess(mockModel, "IMAGE");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success");

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetModelListingOperationWebResourceSuccess() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setModelListingOperationWebResourceSuccess(mockModel);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_success");

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetModelListingOperationResourceTypeErrors() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setModelListingOperationResourceTypeErrors(mockModel, "IMAGE");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_errors");

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetModelListingOperationWebResourceErrors() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setModelListingOperationWebResourceErrors(mockModel);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_errors");

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetModelDisplaySequence() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    // Execute
    Exception expectedException = null;
    try {
      ProjectMediaControllerHelper.setModelDisplaySequence(mockModel, "IMAGE", 1);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;

    try {
      ProjectMediaControllerHelper.setModelDisplaySequence(mockModel, "VIDEO", 1);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_VIDEO_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;

    try {
      ProjectMediaControllerHelper.setModelDisplaySequence(mockModel, "DOCUMENT", 1);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_DOCUMENT_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;

    try {
      ProjectMediaControllerHelper.setModelDisplaySequence(mockModel, "OTHER", 1);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_OTHER_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;
  }
}
