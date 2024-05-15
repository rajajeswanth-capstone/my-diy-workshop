package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.springframework.stereotype.Component;

/**
 * Get Project Web Resource By Id Response Command
 */
@Component
public class GetProjectWebResourceByWebResourceIdResponseCommand implements ICommand<CommandDTO> {
  /**
   * Build Project web resource by id response
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectWebResource diyProjectWebResource = (DiyProjectWebResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE);
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder()
        .id(diyProjectWebResource.getId())
        .title(diyProjectWebResource.getTitle())
        .link(diyProjectWebResource.getLink())
        .type(diyProjectWebResource.getType())
        .description(diyProjectWebResource.getDescription())
        .build();

    dto.add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation);
  }
}
