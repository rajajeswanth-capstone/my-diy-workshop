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
 * Diy Project Web Resource entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class DiyProjectWebResource {
  /**
   * Unique identifier for the project web resource
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
   * Web Resource Sequence
   */
  @NotNull(message = "Web Resource Sequence is mandatory")
  private Long webResourceSequence;

  /**
   * Web Resource Link
   */
  private String link;

  /**
   * Title of the Web Resource
   */
  @NotBlank(message = "Title is mandatory")
  private String title;

  /**
   * Description of the Web Resource
   */
  private String description;

  /**
   * Type of Resource (Plan or Web)
   */
  @NotBlank(message = "Type is mandatory")
  private String type;

  /**
   * Type of Web Resource
   */
  private String resourceType;
}
