package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.web.multipart.MultipartFile;

public class UpdateProjectLocalResourceFileCommandTest {
  @Test
  public void testProcess() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

      DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).link("link").build();
      DiyProject diyProject = DiyProject.builder().id(1L).build();
      ProjectDetailLocalResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailLocalResourceRepresentation
          .builder()
          .id(1L).title("title").localResourceSequence(1L).type("type").resourceType("resourceType").description("description")
          .build();
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
          .add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource)
          .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE, mockMultipartFile)
          .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailWebResourceRepresentation)
          .build();

      UpdateProjectLocalResourceFileCommand command = new UpdateProjectLocalResourceFileCommand();

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.buildLocalResourceFilePath( 1L, 1L, mockMultipartFile)).thenReturn("path");
      mockFileUtility.when(() -> FileUtility.deleteLocalResourceByLink("link")).thenAnswer((Answer<Void>) invocation -> null);
      mockFileUtility.when(() -> FileUtility.saveLocalResource(mockMultipartFile, "path")).thenAnswer((Answer<Void>) invocation -> null);
      mockFileUtility.when(() -> FileUtility.buildFileLink(1L, 1L, mockMultipartFile)).thenReturn("link");
      mockFileUtility.when(() -> FileUtility.buildFileName(1L, "originalFileName")).thenReturn("fileName");
      Mockito.when(mockMultipartFile.getOriginalFilename()).thenReturn("originalFileName");

      // Execute
      Exception expectedException = null;
      try {
        command.process(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
      DiyProjectLocalResource diyProjectLocalResourceResponse = (DiyProjectLocalResource) commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);
      assert diyProjectLocalResourceResponse.getLink().equals("link");
      assert diyProjectLocalResourceResponse.getOriginalFileName().equals("fileName");
    }
  }
}
