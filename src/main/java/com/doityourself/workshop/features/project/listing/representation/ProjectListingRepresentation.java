package com.doityourself.workshop.features.project.listing.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Project Listing Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProjectListingRepresentation {
  private Long id;
  private String title;
  private String shortDescription;
  private String imageLink;
  private Integer displaySequence;
}
