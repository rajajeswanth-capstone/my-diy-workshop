package com.doityourself.workshop.features.project.share.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.database.entities.DiyUserProjectId;
import com.doityourself.workshop.features.project.share.exception.ProjectShareFailedException;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import org.junit.jupiter.api.Test;

public class ShareProjectValidationsTest {
  @Test
  public void testValidateDeleteProjectLocalResourceRequest() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateShareProjectRequest(
          ShareProjectRepresentation.builder().projectId(1L).sharedUserId(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDeleteProjectLocalResourceRequestRequired() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateShareProjectRequest(
          ShareProjectRepresentation.builder().sharedUserId(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((ProjectShareFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");

    expectedException = null;
    try {
      shareProjectValidations.validateShareProjectRequest(
          ShareProjectRepresentation.builder().projectId(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((ProjectShareFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");
  }

  @Test
  public void testValidateDiyProjectEntity() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateDiyProjectEntity(
          DiyProject.builder().id(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectEntityRequired() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateDiyProjectEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((ProjectShareFailedException) expectedException).getMessages().get(0).equals("message2");
  }

  @Test
  public void testValidateSharedDiyUserEntity() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateSharedDiyUserEntity(
          DiyUser.builder().id(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateSharedDiyUserEntityRequired() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateSharedDiyUserEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((ProjectShareFailedException) expectedException).getMessages().get(0).equals("message2");
  }

  @Test
  public void testValidateDiyUserProjectAssociation() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateDiyUserProjectAssociation(
          DiyUserProject
              .builder()
              .id(
                  DiyUserProjectId.builder().diyUser(DiyUser.builder().id(1L).build()).diyProject(DiyProject.builder().id(1L).build()).build()
              )
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyUserProjectAssociationRequired() {
    // Initialize
    ShareProjectValidations shareProjectValidations = new ShareProjectValidations();
    shareProjectValidations.projectShareAssociationFailedErrorMessage = "message1";
    shareProjectValidations.projectShareFailedErrorMessage = "message2";
    shareProjectValidations.projectShareProjectIdRequiredErrorMessage = "field1message1";
    shareProjectValidations.projectShareProjectIdFieldName = "field1";
    shareProjectValidations.projectShareUserIdRequiredErrorMessage = "field2message1";
    shareProjectValidations.projectShareUserIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectValidations.validateDiyUserProjectAssociation(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((ProjectShareFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
