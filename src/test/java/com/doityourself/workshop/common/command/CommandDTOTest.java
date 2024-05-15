package com.doityourself.workshop.common.command;

import org.junit.jupiter.api.Test;

public class CommandDTOTest {
  @Test
  public void testBuild() {
    // Execute
    CommandDTO commandDTO = CommandDTO.builder().build();

    // Assertions
    assert commandDTO != null;
  }

  @Test
  public void testAdd() {
    // Execute
    CommandDTO commandDTO = CommandDTO.builder().add("a", "b").build();

    // Assertions
    assert commandDTO.get("a") == "b";
  }

  @Test
  public void testRemove() {
    // Execute
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add("a", "b")
        .add("c", "d")
        .remove("a")
        .build();

    // Assertions
    assert commandDTO.get("a") == null;
    assert commandDTO.get("c") == "d";
  }
}
