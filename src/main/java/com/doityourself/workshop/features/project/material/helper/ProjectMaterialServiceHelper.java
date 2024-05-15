package com.doityourself.workshop.features.project.material.helper;

import com.doityourself.workshop.common.utility.DecimalUtility;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project Material Service helper
 */
@Component
public class ProjectMaterialServiceHelper {
  private static final DecimalFormat TOTAL_DECIMAL_FORMAT = new DecimalFormat("##.00");

  /**
   * Method to build a list of {@link ProjectDetailMaterialRepresentation} from {@link DiyProjectMaterial} entities
   *
   * @param diyProjectMaterials {@link List}&lt;{@link DiyProjectMaterial}&gt;
   * @return {@link List}&lt;{@link ProjectDetailMaterialRepresentation}&gt;
   */
  public List<ProjectDetailMaterialRepresentation> buildMaterials(List<DiyProjectMaterial> diyProjectMaterials) {

    return Optional.ofNullable(diyProjectMaterials)
        .map(list -> list.stream()
            .filter(Objects::nonNull)
            .map(it ->
                (ProjectDetailMaterialRepresentation) ProjectDetailMaterialRepresentation.builder()
                    .id(it.getId())
                    .materialDescription(it.getMaterialDescription())
                    .materialSequence(it.getMaterialSequence())
                    .units(it.getUnits())
                    .pricePerUnit(it.getPricePerUnit())
                    .vendor(it.getVendor())
                    .cost(DecimalUtility.formatDecimal(TOTAL_DECIMAL_FORMAT, it.getUnits() * it.getPricePerUnit()))
                    .build()
            ).collect(Collectors.toList())
        )
        .orElse(null);
  }

  /**
   * Method to group materials by vendors
   *
   * @param materials {@link List}&lt;{@link ProjectDetailMaterialRepresentation}&gt;
   * @return {@link Map}&lt;{@link String}, {@link List}&lt;{@link ProjectDetailMaterialRepresentation}&gt;&gt;
   */
  public Map<String, List<ProjectDetailMaterialRepresentation>> groupMaterialsByVendors(List<ProjectDetailMaterialRepresentation> materials) {
    return materials.stream().collect(Collectors.groupingBy(projectDetailMaterialRepresentation -> Optional.ofNullable(projectDetailMaterialRepresentation.getVendor()).orElse("Other")));
  }

  /**
   * Method to calculate total project cost
   *
   * @param diyProjectMaterials {@link List}&lt;{@link DiyProjectMaterial}&gt;
   * @return {@link Double}
   */
  public Double calculateTotalProjectCost(List<DiyProjectMaterial> diyProjectMaterials) {
    return Optional.ofNullable(diyProjectMaterials)
        .map(list -> list.stream()
            .filter(Objects::nonNull)
            .map(it -> it.getUnits() * it.getPricePerUnit())
            .reduce(0.0D, Double::sum)
        )
        .orElse(0.0D);
  }

  /**
   * Method to get over budget amount
   *
   * @param totalProjectCost {@link Double}
   * @param budget {@link Double}
   * @return {@link Double}
   */
  public Double overBudgetAmount(Double totalProjectCost, Double budget) {
    return Optional.ofNullable(budget)
        .map(it -> (budget > 0 && totalProjectCost > budget) ? (totalProjectCost - budget) : 0.0D)
        .orElse(0.0D);
  }
}
