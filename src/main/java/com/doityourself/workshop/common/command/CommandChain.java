package com.doityourself.workshop.common.command;

import com.doityourself.workshop.common.exception.DiyWorkshopException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Command Chain
 */
@Slf4j
@Component
public class CommandChain {
  @Autowired
  ApplicationContext applicationContext;

  /**
   * Method to execute command chain commands
   *
   * @param dto {@link CommandDTO}
   * @param commands {@link List}&lt;{@link Class}&lt;? extends {@link ICommand}&lt;CommandDTO&gt;&gt;&gt;
   * @param exceptionHandler {@link ICommandExceptionHandler}&lt;{@link CommandDTO}&gt;
   */
  public void execute(CommandDTO dto, List<Class<? extends ICommand<CommandDTO>>> commands, Class<? extends ICommandExceptionHandler<CommandDTO>> exceptionHandler) {
    for (Class<? extends ICommand<CommandDTO>> i : commands) {
      try {
        ICommand<CommandDTO> command = applicationContext.getBean(i);
        command.preProcess(dto);
        command.process(dto);
        command.postProcess(dto);
      } catch (DiyWorkshopException exception) {
        throw exception;
      } catch (RuntimeException exception) {
        if (!Objects.isNull(exceptionHandler)) {
          ICommandExceptionHandler<CommandDTO> exceptionHandlerBean = applicationContext.getBean(exceptionHandler);
          exceptionHandlerBean.handleRuntimeException(dto, exception);
        } else {
          throw exception;
        }
      } catch (Exception exception) {
        if (!Objects.isNull(exceptionHandler)) {
          ICommandExceptionHandler<CommandDTO> exceptionHandlerBean = applicationContext.getBean(exceptionHandler);
          exceptionHandlerBean.handleException(dto, exception);
        } else {
          throw exception;
        }
      }
    }
  }

  /**
   * Method to execute command chain commands
   *
   * @param dto {@link CommandDTO}
   * @param commands {@link List}&lt;{@link Class}&lt;? extends {@link ICommand}&lt;CommandDTO&gt;&gt;&gt;
   */
  public void execute(CommandDTO dto, List<Class<? extends ICommand<CommandDTO>>> commands) {
    execute(dto, commands, null);
  }
}
