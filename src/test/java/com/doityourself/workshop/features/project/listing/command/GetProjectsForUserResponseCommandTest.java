package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetProjectsForUserResponseCommandTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testProcess() {
    // Initialize
    List<DiyProject> requestProjects = new ArrayList<>();
    requestProjects.add(
        DiyProject
            .builder()
            .id(1L).title("title").shortDescription("short").imageLink("link")
            .build()
    );

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECTS, requestProjects)
        .build();

    GetProjectsForUserResponseCommand command = new GetProjectsForUserResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert ((List<ProjectListingRepresentation>) commandDTO.get(ContextConstants.CONTEXT_PROJECTS)).get(0).getId() == 1L;
    assert ((List<ProjectListingRepresentation>) commandDTO.get(ContextConstants.CONTEXT_PROJECTS)).get(0).getTitle().equals("title");
    assert ((List<ProjectListingRepresentation>) commandDTO.get(ContextConstants.CONTEXT_PROJECTS)).get(0).getShortDescription().equals("short");
    assert ((List<ProjectListingRepresentation>) commandDTO.get(ContextConstants.CONTEXT_PROJECTS)).get(0).getImageLink().equals("link");
    assert ((List<ProjectListingRepresentation>) commandDTO.get(ContextConstants.CONTEXT_PROJECTS)).get(0).getDisplaySequence() == 1L;
  }
}
