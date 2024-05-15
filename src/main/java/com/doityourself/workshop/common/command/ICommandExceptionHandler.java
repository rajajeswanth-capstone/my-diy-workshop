package com.doityourself.workshop.common.command;

/**
 * Command Exception Handler
 *
 * @param <I>
 */
public interface ICommandExceptionHandler<I extends CommandDTO> {
  /**
   * Handle {@link RuntimeException}
   *
   * @param dto I
   * @param exception {@link RuntimeException}
   */
  default void handleRuntimeException(I dto, RuntimeException exception) {}

  /**
   * Handle {@link Exception}
   *
   * @param dto I
   * @param exception {@link Exception}
   */
  default void handleException(I dto, Exception exception) {}
}
