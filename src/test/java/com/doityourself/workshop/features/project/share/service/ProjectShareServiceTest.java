package com.doityourself.workshop.features.project.share.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.project.share.command.GetSharedProjectByIdCommand;
import com.doityourself.workshop.features.project.share.command.GetSharedUserCommand;
import com.doityourself.workshop.features.project.share.command.SaveShareUserProjectAssociationCommand;
import com.doityourself.workshop.features.project.share.command.ShareProjectRequestValidationCommand;
import com.doityourself.workshop.features.project.share.exceptionHandler.ShareProjectGlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class ProjectShareServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testShareProject() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<ShareProjectGlobalExceptionHandler>> shareProjectGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectShareService projectShareService = new ProjectShareService();
    projectShareService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectShareService.shareProject(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), shareProjectGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == ShareProjectRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetSharedProjectByIdCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetSharedUserCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == SaveShareUserProjectAssociationCommand.class;
    assert shareProjectGlobalExceptionHandlerCaptor.getValue() == ShareProjectGlobalExceptionHandler.class;
  }
}
