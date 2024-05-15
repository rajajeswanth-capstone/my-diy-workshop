package com.doityourself.workshop.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Diy Project Instruction entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class DiyProjectInstruction {
  /**
   * Unique identifier for the project instruction
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Foreign key relationship with project
   */
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private DiyProject diyProject;

  /**
   * Instruction sequence
   */
  @NotNull(message = "Instruction Sequence is mandatory")
  private Long instructionSequence;

  /**
   * Instruction
   */
  private String instruction;

  /**
   * Title of the instruction
   */
  @NotBlank(message = "Title is mandatory")
  private String title;
}
