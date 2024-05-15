package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.springframework.stereotype.Component;

/**
 * Get Project Local Resource By Id Response Command
 */
@Component
public class GetProjectLocalResourceByLocalResourceIdResponseCommand implements ICommand<CommandDTO> {
  /**
   * Build Project local resource by id response
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectLocalResource diyProjectLocalResource = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation.builder()
        .id(diyProjectLocalResource.getId())
        .title(diyProjectLocalResource.getTitle())
        .link(diyProjectLocalResource.getLink())
        .type(diyProjectLocalResource.getType())
        .resourceType(diyProjectLocalResource.getResourceType())
        .description(diyProjectLocalResource.getDescription())
        .build();

    dto.add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation);
    dto.add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT, projectDetailLocalResourceRepresentation.getResourceType());
  }
}
