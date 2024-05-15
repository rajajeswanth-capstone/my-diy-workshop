package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.junit.jupiter.api.Test;

public class GetProjectLocalResourceByLocalResourceIdResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource
        .builder()
        .id(1L).title("title").link("link").type("type").resourceType("resourceType").description("description")
        .build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource).build();

    GetProjectLocalResourceByLocalResourceIdResponseCommand command = new GetProjectLocalResourceByLocalResourceIdResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = (ProjectDetailLocalResourceRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE);
    assert projectDetailLocalResourceRepresentation.getId() == 1L;
    assert projectDetailLocalResourceRepresentation.getTitle().equals("title");
    assert projectDetailLocalResourceRepresentation.getLink().equals("link");
    assert projectDetailLocalResourceRepresentation.getType().equals("type");
    assert projectDetailLocalResourceRepresentation.getResourceType().equals("resourceType");
    assert projectDetailLocalResourceRepresentation.getDescription().equals("description");
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
    assert commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT).equals("resourceType");
  }
}
