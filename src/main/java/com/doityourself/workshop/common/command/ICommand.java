package com.doityourself.workshop.common.command;

/**
 * Generic Command Interface
 *
 * @param <I>
 */
public interface ICommand<I extends CommandDTO> {
  /**
   * Pre-process a command
   *
   * @param dto {@link I}
   */
  default void preProcess(I dto) {}

  /**
   * Process a command
   *
   * @param dto {@link I}
   */
  default void process(I dto) {}

  /**
   * Post-process a command
   *
   * @param dto {@link I}
   */
  default void postProcess(I dto) {}
}
