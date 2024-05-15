package com.doityourself.workshop.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Diy Project Material entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class DiyProjectMaterial {
  /**
   * Unique identifier for the project material
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
   * Material sequence
   */
  @NotNull(message = "Material Sequence is mandatory")
  private Long materialSequence;

  /**
   * Material Description
   */
  private String materialDescription;

  /**
   * Vendor Name
   */
  private String vendor;

  /**
   * Title of the instruction
   */
  private Long units;

  /**
   * Price per unit
   */
  private Double pricePerUnit;
}
