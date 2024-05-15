package com.doityourself.workshop.features.project.material.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailByProjectIdCommand;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailRequestValidationCommand;
import com.doityourself.workshop.features.project.detail.exceptionHandler.GetProjectDetailGlobalExceptionHandler;
import com.doityourself.workshop.features.project.material.command.*;
import com.doityourself.workshop.features.project.material.exceptionHandler.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class ProjectMaterialServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjectMaterials() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectDetailGlobalExceptionHandler>> getProjectDetailGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.getProjectMaterials(commandDTO);
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
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectMaterialsByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == GetProjectMaterialsResponseCommand.class;
    assert getProjectDetailGlobalExceptionHandlerCaptor.getValue() == GetProjectDetailGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testPrintProjectMaterials() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectDetailGlobalExceptionHandler>> getProjectDetailGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.printProjectMaterials(commandDTO);
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
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectMaterialsByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == GetProjectMaterialsPrintResponseCommand.class;
    assert getProjectDetailGlobalExceptionHandlerCaptor.getValue() == GetProjectDetailGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSaveProjectBudget() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectBudgetGlobalExceptionHandler>> saveProjectBudgetGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.saveProjectBudget(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), saveProjectBudgetGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectMaterialDetailFromProjectDetailRepresentationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == SaveProjectBudgetCommand.class;
    assert saveProjectBudgetGlobalExceptionHandlerCaptor.getValue() == SaveProjectBudgetGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testDeleteProjectBudget() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectBudgetGlobalExceptionHandler>> saveProjectBudgetGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.deleteProjectBudget(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), saveProjectBudgetGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == DeleteProjectBudgetCommand.class;
    assert saveProjectBudgetGlobalExceptionHandlerCaptor.getValue() == SaveProjectBudgetGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSaveProjectMaterial() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectMaterialGlobalExceptionHandler>> saveProjectMaterialGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.saveProjectMaterial(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), saveProjectMaterialGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == SaveProjectMaterialRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectDetailByProjectIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == SaveProjectMaterialCommand.class;
    assert saveProjectMaterialGlobalExceptionHandlerCaptor.getValue() == SaveProjectMaterialGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateProjectMaterial() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<UpdateProjectMaterialGlobalExceptionHandler>> updateProjectMaterialGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.updateProjectMaterial(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), updateProjectMaterialGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == UpdateProjectMaterialRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectMaterialFromProjectMaterialRepresentationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == UpdateProjectMaterialCommand.class;
    assert updateProjectMaterialGlobalExceptionHandlerCaptor.getValue() == UpdateProjectMaterialGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjectMaterial() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectMaterialGlobalExceptionHandler>> getProjectMaterialGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.getProjectMaterial(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), getProjectMaterialGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectMaterialRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetProjectMaterialByMaterialIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetProjectMaterialByMaterialIdResponseCommand.class;
    assert getProjectMaterialGlobalExceptionHandlerCaptor.getValue() == GetProjectMaterialGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testDeleteProjectMaterial() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<DeleteProjectMaterialGlobalExceptionHandler>> deleteProjectMaterialGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectMaterialService projectMaterialService = new ProjectMaterialService();
    projectMaterialService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectMaterialService.deleteProjectMaterial(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), deleteProjectMaterialGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == DeleteProjectMaterialRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == DeleteProjectMaterialByMaterialIdCommand.class;
    assert deleteProjectMaterialGlobalExceptionHandlerCaptor.getValue() == DeleteProjectMaterialGlobalExceptionHandler.class;
  }
}
