package com.doityourself.workshop.features.project.detail.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.project.detail.command.*;
import com.doityourself.workshop.features.project.detail.exceptionHandler.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class ProjectDetailServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjectDetail() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectDetailCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectDetailGlobalExceptionHandler>> getProjectDetailGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectDetailService projectDetailService = new ProjectDetailService();
    projectDetailService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectDetailService.getProjectDetail(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectDetailCommandDTOCaptor.capture(), getProjectDetailCommandsCaptor.capture(), getProjectDetailGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectDetailCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectDetailCommandsCaptor.getValue().get(0) == GetProjectDetailRequestValidationCommand.class;
    assert getProjectDetailCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectDetailCommandsCaptor.getValue().get(2) == GetProjectDetailInstructionsCommand.class;
    assert getProjectDetailCommandsCaptor.getValue().get(3) == GetProjectDetailResponseCommand.class;
    assert getProjectDetailGlobalExceptionHandlerCaptor.getValue() == GetProjectDetailGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSaveProjectDescription() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> saveProjectDescriptionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> saveProjectDescriptionCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectDescriptionGlobalExceptionHandler>> saveProjectDescriptionGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectDetailService projectDetailService = new ProjectDetailService();
    projectDetailService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectDetailService.saveProjectDescription(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(saveProjectDescriptionCommandDTOCaptor.capture(), saveProjectDescriptionCommandsCaptor.capture(), saveProjectDescriptionGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert saveProjectDescriptionCommandDTOCaptor.getValue() == commandDTO;
    assert saveProjectDescriptionCommandsCaptor.getValue().get(0) == GetProjectDetailFromProjectDetailRepresentationCommand.class;
    assert saveProjectDescriptionCommandsCaptor.getValue().get(1) == SaveProjectDescriptionCommand.class;
    assert saveProjectDescriptionGlobalExceptionHandlerCaptor.getValue() == SaveProjectDescriptionGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSaveProjectInstruction() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> saveProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> saveProjectInstructionCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectInstructionGlobalExceptionHandler>> saveProjectInstructionGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectDetailService projectDetailService = new ProjectDetailService();
    projectDetailService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectDetailService.saveProjectInstruction(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(saveProjectInstructionCommandDTOCaptor.capture(), saveProjectInstructionCommandsCaptor.capture(), saveProjectInstructionGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert saveProjectInstructionCommandDTOCaptor.getValue() == commandDTO;
    assert saveProjectInstructionCommandsCaptor.getValue().get(0) == SaveProjectInstructionRequestValidationCommand.class;
    assert saveProjectInstructionCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert saveProjectInstructionCommandsCaptor.getValue().get(2) == SaveProjectInstructionCommand.class;
    assert saveProjectInstructionGlobalExceptionHandlerCaptor.getValue() == SaveProjectInstructionGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateProjectInstruction() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> updateProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> updateProjectInstructionCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<UpdateProjectInstructionGlobalExceptionHandler>> updateProjectInstructionGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectDetailService projectDetailService = new ProjectDetailService();
    projectDetailService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectDetailService.updateProjectInstruction(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(updateProjectInstructionCommandDTOCaptor.capture(), updateProjectInstructionCommandsCaptor.capture(), updateProjectInstructionGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert updateProjectInstructionCommandDTOCaptor.getValue() == commandDTO;
    assert updateProjectInstructionCommandsCaptor.getValue().get(0) == UpdateProjectInstructionRequestValidationCommand.class;
    assert updateProjectInstructionCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert updateProjectInstructionCommandsCaptor.getValue().get(2) == GetProjectInstructionFromProjectInstructionRepresentationCommand.class;
    assert updateProjectInstructionCommandsCaptor.getValue().get(3) == UpdateProjectInstructionCommand.class;
    assert updateProjectInstructionGlobalExceptionHandlerCaptor.getValue() == UpdateProjectInstructionGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjectInstruction() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectInstructionCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectInstructionGlobalExceptionHandler>> getProjectInstructionGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectDetailService projectDetailService = new ProjectDetailService();
    projectDetailService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectDetailService.getProjectInstruction(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectInstructionCommandDTOCaptor.capture(), getProjectInstructionCommandsCaptor.capture(), getProjectInstructionGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectInstructionCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectInstructionCommandsCaptor.getValue().get(0) == GetProjectInstructionRequestValidationCommand.class;
    assert getProjectInstructionCommandsCaptor.getValue().get(1) == GetProjectInstructionByInstructionIdCommand.class;
    assert getProjectInstructionCommandsCaptor.getValue().get(2) == GetProjectInstructionByInstructionIdResponseCommand.class;
    assert getProjectInstructionGlobalExceptionHandlerCaptor.getValue() == GetProjectInstructionGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testDeleteProjectInstruction() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> deleteProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> deleteProjectInstructionCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<DeleteProjectInstructionGlobalExceptionHandler>> deleteProjectInstructionGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectDetailService projectDetailService = new ProjectDetailService();
    projectDetailService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectDetailService.deleteProjectInstruction(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(deleteProjectInstructionCommandDTOCaptor.capture(), deleteProjectInstructionCommandsCaptor.capture(), deleteProjectInstructionGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert deleteProjectInstructionCommandDTOCaptor.getValue() == commandDTO;
    assert deleteProjectInstructionCommandsCaptor.getValue().get(0) == DeleteProjectInstructionRequestValidationCommand.class;
    assert deleteProjectInstructionCommandsCaptor.getValue().get(1) == DeleteProjectInstructionByInstructionIdCommand.class;
    assert deleteProjectInstructionGlobalExceptionHandlerCaptor.getValue() == DeleteProjectInstructionGlobalExceptionHandler.class;
  }
}
