package com.doityourself.workshop.common.helper;

import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

public class SharedProjectHelperTest {
  @Test
  public void testSetSharedProjectIndicatorTrue() {
    // Initialize
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    ArgumentCaptor<Boolean> projectIndicatorCaptor = ArgumentCaptor.forClass(Boolean.class);
    Long projectId = 1L;
    ProjectListingRepresentation project = new ProjectListingRepresentation();
    project.setId(1L);

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(ModelConstants.MODEL_SHARED_PROJECTS)).thenReturn(Collections.singletonList(project));

    // Execute
    Exception expectedException = null;
    try {
      SharedProjectHelper.setSharedProjectIndicator(mockHttpServletRequest, projectId);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.any(), projectIndicatorCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert projectIndicatorCaptor.getValue();
  }

  @Test
  public void testSetSharedProjectIndicatorFalse() {
    // Initialize
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    ArgumentCaptor<Boolean> projectIndicatorCaptor = ArgumentCaptor.forClass(Boolean.class);
    Long projectId = 1L;
    ProjectListingRepresentation project = new ProjectListingRepresentation();
    project.setId(2L);

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(ModelConstants.MODEL_SHARED_PROJECTS)).thenReturn(Collections.singletonList(project));

    // Execute
    Exception expectedException = null;
    try {
      SharedProjectHelper.setSharedProjectIndicator(mockHttpServletRequest, projectId);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.any(), projectIndicatorCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert !projectIndicatorCaptor.getValue();
  }
}
