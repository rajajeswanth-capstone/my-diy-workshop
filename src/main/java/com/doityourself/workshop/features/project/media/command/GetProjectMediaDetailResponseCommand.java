package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.enums.LocalResourceType;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Get Project Media Detail Response Command
 */
@Component
public class GetProjectMediaDetailResponseCommand implements ICommand<CommandDTO> {
  /**
   * Process get project media detail response. Build response object
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    List<DiyProjectLocalResource> diyProjectLocalResources = (List<DiyProjectLocalResource>) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCES);
    List<DiyProjectWebResource> diyProjectWebResources = (List<DiyProjectWebResource>) dto.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCES);
    List<String> webResourceTypes = (List<String>) dto.get(ContextConstants.CONTEXT_WEB_RESOURCE_TYPES);
    List<String> localResourceTypes = (List<String>) dto.get(ContextConstants.CONTEXT_LOCAL_RESOURCE_TYPES);
    AtomicInteger displayCount = new AtomicInteger();

    ProjectDetailRepresentation projectDetail = ProjectDetailRepresentation.builder()
        .id(diyProject.getId())
        .title(diyProject.getTitle())
        .description(diyProject.getDescription())
        .webResourceTypes(webResourceTypes)
        .localResourceTypes(localResourceTypes)
        .imageResources(
            filterResourcesByResourceType(diyProjectLocalResources, LocalResourceType.IMAGE)
        )
        .videoResources(
            filterResourcesByResourceType(diyProjectLocalResources, LocalResourceType.VIDEO)
        )
        .documentResources(
            filterResourcesByResourceType(diyProjectLocalResources, LocalResourceType.DOCUMENT)
        )
        .otherResources(
            filterResourcesByResourceType(diyProjectLocalResources, LocalResourceType.OTHER)
        )
        .webResources(
            Optional.ofNullable(diyProjectWebResources)
                .map(list -> list.stream()
                    .filter(Objects::nonNull)
                    .map(it ->
                        ProjectDetailWebResourceRepresentation.builder()
                            .id(it.getId())
                            .title(it.getTitle())
                            .link(it.getLink())
                            .type(it.getType())
                            .description(it.getDescription())
                            .webResourceSequence(it.getWebResourceSequence())
                            .resourceType(it.getResourceType())
                            .displaySequence(displayCount.incrementAndGet())
                            .build()
                    ).collect(Collectors.toList())
                )
                .orElse(null)
        ).build();

    dto.add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetail);
  }

  /**
   * Method to filter resources by resource type
   *
   * @param diyProjectLocalResources {@link List}&lt;{@link DiyProjectLocalResource}&gt;
   * @param localResourceType {@link LocalResourceType}
   * @return {@link List}&lt;{@link ProjectDetailLocalResourceRepresentation}&gt;
   */
  private List<ProjectDetailLocalResourceRepresentation> filterResourcesByResourceType(List<DiyProjectLocalResource> diyProjectLocalResources, LocalResourceType localResourceType) {
    AtomicInteger integer = new AtomicInteger();
    return Optional.ofNullable(diyProjectLocalResources)
        .map(list -> list.stream()
            .filter(Objects::nonNull)
            .filter(it -> localResourceType.name().equals(it.getResourceType()))
            .map(it ->
                (ProjectDetailLocalResourceRepresentation) ProjectDetailLocalResourceRepresentation.builder()
                    .id(it.getId())
                    .title(it.getTitle())
                    .link(it.getLink())
                    .type(it.getType())
                    .description(it.getDescription())
                    .resourceType(it.getResourceType())
                    .localResourceSequence(it.getLocalResourceSequence())
                    .displaySequence(integer.incrementAndGet())
                    .build()
            ).collect(Collectors.toList())
        )
        .orElse(null);
  }
}
