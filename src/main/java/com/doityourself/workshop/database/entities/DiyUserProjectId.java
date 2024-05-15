package com.doityourself.workshop.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Composite Primary Key for {@link DiyUserProject}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@SuperBuilder(toBuilder = true)
public class DiyUserProjectId implements Serializable {
  /**
   * Diy User entity relationship
   */
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private DiyUser diyUser;

  /**
   * Diy Project entity relationship
   */
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private DiyProject diyProject;
}
