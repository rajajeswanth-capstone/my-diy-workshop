package com.doityourself.workshop.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Diy Project entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class DiyProject {
  /**
   * Unique identifier for the project
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Title of the project
   */
  @NotBlank(message = "Title is mandatory")
  private String title;

  /**
   * Description of the project
   */
  private String description;

  /**
   * Description of the project
   */
  private String shortDescription;

  /**
   * Project image link
   */
  private String imageLink;

  /**
   * Image Content
   */
  @Lob
  private byte[] imageContent;

  /**
   * Budget for the project
   */
  private Double budget;
}
