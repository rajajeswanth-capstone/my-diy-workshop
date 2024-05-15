package com.doityourself.workshop.features.project.listing.helper;

import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

public class ProjectListingHelperTest {
  @Test
  public void testSetOperationUpdate() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation.builder().id(1L).build();

    // Define Mocks
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_UPDATE)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SAVE)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    try {
      ProjectListingHelper.setOperation(mockModel, projectListingRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_UPDATE);

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testSetOperationSave() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);

    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation.builder().build();

    // Define Mocks
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_UPDATE)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SAVE)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    try {
      ProjectListingHelper.setOperation(mockModel, projectListingRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SAVE);

    // Assertions
    assert expectedException == null;
  }
}
