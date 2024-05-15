package com.doityourself.workshop.common.command;

import com.doityourself.workshop.common.exception.DiyWorkshopException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.util.Collections;

public class CommandChainTest {
  @Test
  public void testExecute() {
    // Initialize
    ApplicationContext mockApplicationContext = Mockito.mock(ApplicationContext.class);
    CommandChainTestCommand mockCommand = Mockito.mock(CommandChainTestCommand.class);
    CommandChain commandChain = new CommandChain();
    commandChain.applicationContext = mockApplicationContext;

    // Define Mocks
    Mockito.when(mockApplicationContext.getBean(CommandChainTestCommand.class)).thenReturn(mockCommand);

    // Execute
    commandChain.execute(CommandDTO.builder().build(), Collections.singletonList(CommandChainTestCommand.class));

    // Verify
    Mockito.verify(mockCommand, Mockito.times(1)).preProcess(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(1)).process(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(1)).postProcess(Mockito.any());
  }

  @Test
  public void testExecuteDiyWorkshopException() {
    // Initialize
    ApplicationContext mockApplicationContext = Mockito.mock(ApplicationContext.class);
    CommandChainTestCommand mockCommand = Mockito.mock(CommandChainTestCommand.class);
    CommandChain commandChain = new CommandChain();
    commandChain.applicationContext = mockApplicationContext;

    // Define Mocks
    Mockito.when(mockApplicationContext.getBean(CommandChainTestCommand.class)).thenReturn(mockCommand);
    Mockito.doThrow(new DiyWorkshopException()).when(mockCommand).preProcess(Mockito.any());

    // Execute
    DiyWorkshopException expectedException = null;
    try {
      commandChain.execute(CommandDTO.builder().build(), Collections.singletonList(CommandChainTestCommand.class));
    } catch (DiyWorkshopException exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommand, Mockito.times(1)).preProcess(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).process(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).postProcess(Mockito.any());

    // Assertions
    assert expectedException != null;
  }

  @Test
  public void testExecuteRuntimeException() {
    // Initialize
    ApplicationContext mockApplicationContext = Mockito.mock(ApplicationContext.class);
    CommandChainTestCommand mockCommand = Mockito.mock(CommandChainTestCommand.class);
    CommandChain commandChain = new CommandChain();
    commandChain.applicationContext = mockApplicationContext;

    // Define Mocks
    Mockito.when(mockApplicationContext.getBean(CommandChainTestCommand.class)).thenReturn(mockCommand);
    Mockito.doThrow(new RuntimeException()).when(mockCommand).preProcess(Mockito.any());

    // Execute
    RuntimeException expectedException = null;
    try {
      commandChain.execute(CommandDTO.builder().build(), Collections.singletonList(CommandChainTestCommand.class));
    } catch (RuntimeException exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommand, Mockito.times(1)).preProcess(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).process(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).postProcess(Mockito.any());

    // Assertions
    assert expectedException != null;
  }

  @Test
  public void testExecuteRuntimeExceptionWithExceptionHandler() {
    // Initialize
    ApplicationContext mockApplicationContext = Mockito.mock(ApplicationContext.class);
    CommandChainTestCommand mockCommand = Mockito.mock(CommandChainTestCommand.class);
    CommandChainTestExceptionHandler mockExceptionHandler = Mockito.mock(CommandChainTestExceptionHandler.class);
    CommandChain commandChain = new CommandChain();
    commandChain.applicationContext = mockApplicationContext;

    // Define Mocks
    Mockito.when(mockApplicationContext.getBean(CommandChainTestCommand.class)).thenReturn(mockCommand);
    Mockito.when(mockApplicationContext.getBean(CommandChainTestExceptionHandler.class)).thenReturn(mockExceptionHandler);
    Mockito.doThrow(new RuntimeException()).when(mockCommand).preProcess(Mockito.any());
    Mockito.doThrow(new RuntimeException()).when(mockExceptionHandler).handleRuntimeException(Mockito.any(), Mockito.any());

    // Execute
    RuntimeException expectedException = null;
    try {
      commandChain.execute(CommandDTO.builder().build(), Collections.singletonList(CommandChainTestCommand.class), CommandChainTestExceptionHandler.class);
    } catch (RuntimeException exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommand, Mockito.times(1)).preProcess(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).process(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).postProcess(Mockito.any());
    Mockito.verify(mockExceptionHandler, Mockito.times(1)).handleRuntimeException(Mockito.any(), Mockito.any());

    // Assertions
    assert expectedException != null;
  }

  @Test
  public void testExecuteException() {
    // Initialize
    ApplicationContext mockApplicationContext = Mockito.mock(ApplicationContext.class);
    CommandChainTestCommand mockCommand = Mockito.mock(CommandChainTestCommand.class);
    CommandChain commandChain = new CommandChain();
    commandChain.applicationContext = mockApplicationContext;

    // Define Mocks
    Mockito.when(mockApplicationContext.getBean(CommandChainTestCommand.class)).thenReturn(mockCommand);
    Mockito.doThrow(new ArrayIndexOutOfBoundsException()).when(mockCommand).preProcess(Mockito.any());

    // Execute
    Exception expectedException = null;
    try {
      commandChain.execute(CommandDTO.builder().build(), Collections.singletonList(CommandChainTestCommand.class));
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommand, Mockito.times(1)).preProcess(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).process(Mockito.any());
    Mockito.verify(mockCommand, Mockito.times(0)).postProcess(Mockito.any());

    // Assertions
    assert expectedException != null;
  }

  public static class CommandChainTestCommand implements ICommand<CommandDTO> { }
  public static class CommandChainTestExceptionHandler implements ICommandExceptionHandler<CommandDTO> { }
  public static class TestException extends Exception {}
}
