package com.doityourself.workshop.common.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Command DTO
 */
@Getter
@AllArgsConstructor
public class CommandDTO {
  private Map<String, Object> context;

  /**
   * Static Builder
   *
   * @return {@link CommandDTO}
   */
  public static CommandDTO builder() {
    return new CommandDTO(new HashMap<>());
  }

  /**
   * Build method
   *
   * @return {@link CommandDTO}
   */
  public CommandDTO build() {
    return this;
  }

  /**
   * Method to add entry into context
   *
   * @param key {@link String}
   * @param value {@link Object}
   * @return {@link CommandDTO}
   */
  public CommandDTO add(String key, Object value) {
    context.put(key, value);
    return this;
  }

  /**
   * Method to remove entry from context
   *
   * @param key {@link String}
   * @return {@link CommandDTO}
   */
  public CommandDTO remove(String key) {
    context.remove(key);
    return this;
  }

  /**
   * Method to get entry from context
   *
   * @param key {@link String}
   * @return {@link Object}
   */
  public Object get(String key) {
    return context.get(key);
  }
}
