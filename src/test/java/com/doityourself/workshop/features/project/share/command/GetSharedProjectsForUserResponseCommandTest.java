package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetSharedProjectsForUserResponseCommandTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testProcess() {
    // Initialize
    DiyProject diyProject = DiyProject
        .builder()
        .id(1L).title("title").shortDescription("short").imageLink("link")
        .build();
    List<DiyProject> diyProjects = new ArrayList<>();
    diyProjects.add(diyProject);
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_SHARED_PROJECTS, diyProjects).build();

    GetSharedProjectsForUserResponseCommand command = new GetSharedProjectsForUserResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    List<ProjectListingRepresentation> projects = (List<ProjectListingRepresentation>) commandDTO.get(ContextConstants.CONTEXT_SHARED_PROJECTS);
    assert projects.get(0).getId() == 1L;
    assert projects.get(0).getTitle().equals("title");
    assert projects.get(0).getShortDescription().equals("short");
    assert projects.get(0).getImageLink().equals("link");
  }
}
