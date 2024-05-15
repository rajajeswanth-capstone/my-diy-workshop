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
 * Diy Project Image entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class DiyProjectLocalResource {
  /**
   * Unique identifier for the project image
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
   * Image sequence
   */
  @NotNull(message = "Local Resource Sequence is mandatory")
  private Long localResourceSequence;

  /**
   * Image Link
   */
  private String link;

  /**
   * Title of the image
   */
  @NotBlank(message = "Title is mandatory")
  private String title;

  /**
   * Description of the image
   */
  private String description;

  /**
   * Type of image
   */
  @NotBlank(message = "Type is mandatory")
  private String type;

  /**
   * Original file name
   */
  private String originalFileName;

  /**
   * Resource type
   */
  private String resourceType;

  /**
   * Resource Content
   */
  @Lob
  private byte[] resourceContent;
}
