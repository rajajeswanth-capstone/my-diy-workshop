package com.doityourself.workshop.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Diy User Project entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class DiyUserProject {
  /**
   * Composite Primary key
   */
  @EmbeddedId
  private DiyUserProjectId id;

  /**
   * Indicates if the project is shared
   */
  private boolean shared;
}
