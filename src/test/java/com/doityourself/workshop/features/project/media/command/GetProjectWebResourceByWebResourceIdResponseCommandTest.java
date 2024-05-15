package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.junit.jupiter.api.Test;

public class GetProjectWebResourceByWebResourceIdResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource
        .builder()
        .id(1L).title("title").link("link").type("type").resourceType("resourceType").description("description")
        .build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE, diyProjectWebResource).build();

    GetProjectWebResourceByWebResourceIdResponseCommand command = new GetProjectWebResourceByWebResourceIdResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = (ProjectDetailWebResourceRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE);
    assert projectDetailWebResourceRepresentation.getId() == 1L;
    assert projectDetailWebResourceRepresentation.getTitle().equals("title");
    assert projectDetailWebResourceRepresentation.getLink().equals("link");
    assert projectDetailWebResourceRepresentation.getType().equals("type");
    assert projectDetailWebResourceRepresentation.getDescription().equals("description");
  }
}
