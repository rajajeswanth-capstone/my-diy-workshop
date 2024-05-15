package com.doityourself.workshop.features.project.media.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Project Detail Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProjectDetailRepresentation {
  private Long id;
  private String title;
  private String description;
  @Singular("addLocalResource")
  private List<ProjectDetailLocalResourceRepresentation> localResources;
  @Singular("addImageResource")
  private List<ProjectDetailLocalResourceRepresentation> imageResources;
  @Singular("addVideoResource")
  private List<ProjectDetailLocalResourceRepresentation> videoResources;
  @Singular("addDocumentResource")
  private List<ProjectDetailLocalResourceRepresentation> documentResources;
  @Singular("addOtherResource")
  private List<ProjectDetailLocalResourceRepresentation> otherResources;
  @Singular("addWebResource")
  private List<ProjectDetailWebResourceRepresentation> webResources;
  private List<String> webResourceTypes;
  private List<String> localResourceTypes;
}
