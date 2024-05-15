package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.enums.LocalResourceType;
import com.doityourself.workshop.common.enums.WebResourceType;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetProjectMediaDetailResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyProject diyProject = DiyProject.builder().id(1L).title("title").description("description").build();

    DiyProjectLocalResource imageDiyProjectLocalResource = DiyProjectLocalResource
        .builder()
        .resourceType(LocalResourceType.IMAGE.name()).id(1L).title("title1").link("link1").type("type1").description("description1").localResourceSequence(1L)
        .build();
    DiyProjectLocalResource videoDiyProjectLocalResource = DiyProjectLocalResource
        .builder()
        .resourceType(LocalResourceType.VIDEO.name()).id(2L).title("title2").link("link2").type("type2").description("description2").localResourceSequence(2L)
        .build();
    DiyProjectLocalResource documentDiyProjectLocalResource = DiyProjectLocalResource
        .builder()
        .resourceType(LocalResourceType.DOCUMENT.name()).id(3L).title("title3").link("link3").type("type3").description("description3").localResourceSequence(3L)
        .build();
    DiyProjectLocalResource otherDiyProjectLocalResource = DiyProjectLocalResource
        .builder()
        .resourceType(LocalResourceType.OTHER.name()).id(4L).title("title4").link("link4").type("type4").description("description4").localResourceSequence(4L)
        .build();

    DiyProjectWebResource diyProjectWebResource1 = DiyProjectWebResource
        .builder()
        .id(1L).title("title1").link("link1").type("type1").description("description1").webResourceSequence(1L).resourceType(WebResourceType.WEB.name())
        .build();
    DiyProjectWebResource diyProjectWebResource2 = DiyProjectWebResource
        .builder()
        .id(2L).title("title2").link("link2").type("type2").description("description2").webResourceSequence(2L).resourceType(WebResourceType.WEB.name())
        .build();

    List<DiyProjectWebResource> diyProjectWebResources = new ArrayList<>();
    diyProjectWebResources.add(diyProjectWebResource1);
    diyProjectWebResources.add(diyProjectWebResource2);

    List<DiyProjectLocalResource> diyProjectLocalResources = new ArrayList<>();
    diyProjectLocalResources.add(imageDiyProjectLocalResource);
    diyProjectLocalResources.add(videoDiyProjectLocalResource);
    diyProjectLocalResources.add(documentDiyProjectLocalResource);
    diyProjectLocalResources.add(otherDiyProjectLocalResource);

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCES, diyProjectLocalResources)
        .add(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCES, diyProjectWebResources)
        .add(ContextConstants.CONTEXT_WEB_RESOURCE_TYPES, Arrays.stream(WebResourceType.values()).collect(Collectors.toList()))
        .add(ContextConstants.CONTEXT_LOCAL_RESOURCE_TYPES, Arrays.stream(LocalResourceType.values()).collect(Collectors.toList()))
        .build();

    GetProjectMediaDetailResponseCommand command = new GetProjectMediaDetailResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    ProjectDetailRepresentation projectDetail = (ProjectDetailRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_DETAIL);
    assert projectDetail.getId() == diyProject.getId();
    assert projectDetail.getTitle().equals(diyProject.getTitle());
    assert projectDetail.getDescription().equals(diyProject.getDescription());
    assert projectDetail.getWebResourceTypes() == commandDTO.get(ContextConstants.CONTEXT_WEB_RESOURCE_TYPES);
    assert projectDetail.getLocalResourceTypes() == commandDTO.get(ContextConstants.CONTEXT_LOCAL_RESOURCE_TYPES);

    ProjectDetailLocalResourceRepresentation imageResource = projectDetail.getImageResources().get(0);
    assert imageResource.getId() == imageDiyProjectLocalResource.getId();
    assert imageResource.getTitle().equals(imageDiyProjectLocalResource.getTitle());
    assert imageResource.getLink().equals(imageDiyProjectLocalResource.getLink());
    assert imageResource.getType().equals(imageDiyProjectLocalResource.getType());
    assert imageResource.getDescription().equals(imageDiyProjectLocalResource.getDescription());
    assert imageResource.getResourceType().equals(imageDiyProjectLocalResource.getResourceType());
    assert imageResource.getLocalResourceSequence() == 1L;
    assert imageResource.getDisplaySequence() == 1;

    ProjectDetailLocalResourceRepresentation videoResource = projectDetail.getVideoResources().get(0);
    assert videoResource.getId() == videoDiyProjectLocalResource.getId();
    assert videoResource.getTitle().equals(videoDiyProjectLocalResource.getTitle());
    assert videoResource.getLink().equals(videoDiyProjectLocalResource.getLink());
    assert videoResource.getType().equals(videoDiyProjectLocalResource.getType());
    assert videoResource.getDescription().equals(videoDiyProjectLocalResource.getDescription());
    assert videoResource.getResourceType().equals(videoDiyProjectLocalResource.getResourceType());
    assert videoResource.getLocalResourceSequence() == 2L;
    assert videoResource.getDisplaySequence() == 1;

    ProjectDetailLocalResourceRepresentation documentResource = projectDetail.getDocumentResources().get(0);
    assert documentResource.getId() == documentDiyProjectLocalResource.getId();
    assert documentResource.getTitle().equals(documentDiyProjectLocalResource.getTitle());
    assert documentResource.getLink().equals(documentDiyProjectLocalResource.getLink());
    assert documentResource.getType().equals(documentDiyProjectLocalResource.getType());
    assert documentResource.getDescription().equals(documentDiyProjectLocalResource.getDescription());
    assert documentResource.getResourceType().equals(documentDiyProjectLocalResource.getResourceType());
    assert documentResource.getLocalResourceSequence() == 3L;
    assert documentResource.getDisplaySequence() == 1;

    ProjectDetailLocalResourceRepresentation otherResource = projectDetail.getOtherResources().get(0);
    assert otherResource.getId() == otherDiyProjectLocalResource.getId();
    assert otherResource.getTitle().equals(otherDiyProjectLocalResource.getTitle());
    assert otherResource.getLink().equals(otherDiyProjectLocalResource.getLink());
    assert otherResource.getType().equals(otherDiyProjectLocalResource.getType());
    assert otherResource.getDescription().equals(otherDiyProjectLocalResource.getDescription());
    assert otherResource.getResourceType().equals(otherDiyProjectLocalResource.getResourceType());
    assert otherResource.getLocalResourceSequence() == 4L;
    assert otherResource.getDisplaySequence() == 1;

    ProjectDetailWebResourceRepresentation webResource1 = projectDetail.getWebResources().get(0);
    assert webResource1.getId() == diyProjectWebResource1.getId();
    assert webResource1.getTitle().equals(diyProjectWebResource1.getTitle());
    assert webResource1.getLink().equals(diyProjectWebResource1.getLink());
    assert webResource1.getType().equals(diyProjectWebResource1.getType());
    assert webResource1.getDescription().equals(diyProjectWebResource1.getDescription());
    assert webResource1.getResourceType().equals(diyProjectWebResource1.getResourceType());
    assert webResource1.getWebResourceSequence() == 1L;
    assert webResource1.getDisplaySequence() == 1;

    ProjectDetailWebResourceRepresentation webResource2 = projectDetail.getWebResources().get(1);
    assert webResource2.getId() == diyProjectWebResource2.getId();
    assert webResource2.getTitle().equals(diyProjectWebResource2.getTitle());
    assert webResource2.getLink().equals(diyProjectWebResource2.getLink());
    assert webResource2.getType().equals(diyProjectWebResource2.getType());
    assert webResource2.getDescription().equals(diyProjectWebResource2.getDescription());
    assert webResource2.getResourceType().equals(diyProjectWebResource2.getResourceType());
    assert webResource2.getWebResourceSequence() == 2L;
    assert webResource2.getDisplaySequence() == 2;
  }
}
