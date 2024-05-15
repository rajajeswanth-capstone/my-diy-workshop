package com.doityourself.workshop.features.project.share.command;

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
 * Get shared projects for user response command
 */
@Component
public class GetSharedProjectsForUserResponseCommand implements ICommand<CommandDTO> {
  /**
   * Get shared projects for user response
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(CommandDTO dto) {
    List<DiyProject> diyProjects = (List<DiyProject>) dto.get(EntityConstants.ENTITY_DIY_SHARED_PROJECTS);
    List<ProjectListingRepresentation> projects = new ArrayList<>();
    AtomicInteger integer = new AtomicInteger();

    diyProjects.forEach(i ->
      projects.add(
          ProjectListingRepresentation.builder()
              .title(i.getTitle())
              .id(i.getId())
              .shortDescription(i.getShortDescription())
              .imageLink(i.getImageLink())
              .displaySequence(integer.incrementAndGet())
              .build()
      )
    );
    dto.add(ContextConstants.CONTEXT_SHARED_PROJECTS, projects);
  }
}
