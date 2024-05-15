package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Get Project Detail Response Command
 */
@Component
public class GetProjectDetailResponseCommand implements ICommand<CommandDTO> {
  /**
   * Process get project detail response. Build response object
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    List<DiyProjectInstruction> diyProjectInstructions = (List<DiyProjectInstruction>) dto.get(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTIONS);
    ProjectDetailRepresentation projectDetail = ProjectDetailRepresentation.builder()
        .id(diyProject.getId())
        .title(diyProject.getTitle())
        .shortDescription(diyProject.getShortDescription())
        .description(diyProject.getDescription())
        .instructions(
            Optional.ofNullable(diyProjectInstructions)
                .map(list -> list.stream()
                    .filter(Objects::nonNull)
                    .map(it ->
                        ProjectDetailInstructionRepresentation.builder()
                            .id(it.getId())
                            .title(it.getTitle())
                            .instructionSequence(it.getInstructionSequence())
                            .instruction(it.getInstruction())
                            .build()
                    ).collect(Collectors.toList())
                )
                .orElse(null)
        ).build();

    dto.add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetail);
  }
}
