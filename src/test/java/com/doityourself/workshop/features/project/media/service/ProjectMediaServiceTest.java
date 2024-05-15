package com.doityourself.workshop.features.project.media.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailByProjectIdCommand;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailRequestValidationCommand;
import com.doityourself.workshop.features.project.detail.exceptionHandler.GetProjectDetailGlobalExceptionHandler;
import com.doityourself.workshop.features.project.media.command.*;
import com.doityourself.workshop.features.project.media.exceptionHandler.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class ProjectMediaServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testOpenFile() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectLocalResourceGlobalExceptionHandler>> getProjectLocalResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.openFile(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), getProjectLocalResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectLocalResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectLocalResourceByLocalResourceIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == GetProjectLocalResourceByLocalResourceIdResponseCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(4) == OpenLocalResourceCommand.class;
    assert getProjectLocalResourceGlobalExceptionHandlerCaptor.getValue() == GetProjectLocalResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjectDetail() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectDetailGlobalExceptionHandler>> getProjectDetailGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.getProjectDetail(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), getProjectDetailGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectDetailRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectDetailLocalResourcesCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == GetProjectDetailWebResourcesCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(4) == GetProjectMediaDetailResponseCommand.class;
    assert getProjectDetailGlobalExceptionHandlerCaptor.getValue() == GetProjectDetailGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSaveProjectLocalResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectLocalResourceGlobalExceptionHandler>> saveProjectLocalResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.saveProjectLocalResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), saveProjectLocalResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == SaveProjectLocalResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == SaveProjectLocalResourceCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == SaveProjectLocalResourceFileCommand.class;
    assert saveProjectLocalResourceGlobalExceptionHandlerCaptor.getValue() == SaveProjectLocalResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSaveProjectWebResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectWebResourceGlobalExceptionHandler>> saveProjectWebResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.saveProjectWebResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), saveProjectWebResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == SaveProjectWebResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == SaveProjectWebResourceCommand.class;
    assert saveProjectWebResourceGlobalExceptionHandlerCaptor.getValue() == SaveProjectWebResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateProjectLocalResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<UpdateProjectLocalResourceGlobalExceptionHandler>> updateProjectLocalResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.updateProjectLocalResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), updateProjectLocalResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == UpdateProjectLocalResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectLocalResourceFromProjectLocalResourceRepresentationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == UpdateProjectLocalResourceFileCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(4) == UpdateProjectLocalResourceCommand.class;
    assert updateProjectLocalResourceGlobalExceptionHandlerCaptor.getValue() == UpdateProjectLocalResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateProjectWebResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<UpdateProjectWebResourceGlobalExceptionHandler>> updateProjectWebResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.updateProjectWebResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), updateProjectWebResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == UpdateProjectWebResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectWebResourceFromProjectWebResourceRepresentationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == UpdateProjectWebResourceCommand.class;
    assert updateProjectWebResourceGlobalExceptionHandlerCaptor.getValue() == UpdateProjectWebResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjectLocalResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectLocalResourceGlobalExceptionHandler>> getProjectLocalResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.getProjectLocalResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), getProjectLocalResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectLocalResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectLocalResourceByLocalResourceIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectLocalResourceByLocalResourceIdResponseCommand.class;
    assert getProjectLocalResourceGlobalExceptionHandlerCaptor.getValue() == GetProjectLocalResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjectWebResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectWebResourceGlobalExceptionHandler>> getProjectWebResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.getProjectWebResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), getProjectWebResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectWebResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectWebResourceByWebResourceIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectWebResourceByWebResourceIdResponseCommand.class;
    assert getProjectWebResourceGlobalExceptionHandlerCaptor.getValue() == GetProjectWebResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testDeleteProjectLocalResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<DeleteProjectLocalResourceGlobalExceptionHandler>> deleteProjectLocalResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.deleteProjectLocalResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), deleteProjectLocalResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == DeleteProjectLocalResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectLocalResourceByLocalResourceIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == DeleteProjectLocalResourceFileCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == DeleteProjectLocalResourceByLocalResourceIdCommand.class;
    assert deleteProjectLocalResourceGlobalExceptionHandlerCaptor.getValue() == DeleteProjectLocalResourceGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testDeleteProjectWebResource() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<DeleteProjectWebResourceGlobalExceptionHandler>> deleteProjectWebResourceGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMediaService projectMediaService = new ProjectMediaService();
    projectMediaService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMediaService.deleteProjectWebResource(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), deleteProjectWebResourceGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == DeleteProjectWebResourceRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == DeleteProjectWebResourceByWebResourceIdCommand.class;
    assert deleteProjectWebResourceGlobalExceptionHandlerCaptor.getValue() == DeleteProjectWebResourceGlobalExceptionHandler.class;
  }
}
