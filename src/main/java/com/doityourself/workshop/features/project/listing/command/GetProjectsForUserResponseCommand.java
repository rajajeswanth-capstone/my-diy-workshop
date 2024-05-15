package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Get projects for user Response Command
 */
@Component
public class GetProjectsForUserResponseCommand implements ICommand<CommandDTO> {
  /**
   * Process get projects for user response. Build response object
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(CommandDTO dto) {
    List<DiyProject> diyProjects = (List<DiyProject>) dto.get(EntityConstants.ENTITY_DIY_PROJECTS);
    List<ProjectListingRepresentation> projects = new ArrayList<>();
    AtomicInteger integer = new AtomicInteger();

    diyProjects.forEach(i ->
      projects.add(
          ProjectListingRepresentation.builder()
              .title(i.getTitle())
              .shortDescription(i.getShortDescription())
              .id(i.getId())
              .imageLink(i.getImageLink())
              .displaySequence(integer.incrementAndGet())
              .build()
      )
    );
    dto.add(ContextConstants.CONTEXT_PROJECTS, projects);
  }
}
