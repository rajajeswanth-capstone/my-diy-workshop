package com.doityourself.workshop.features.project.material.helper;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectMaterialServiceHelperTest {
  @Test
  public void testBuildMaterials() {
    // Initialize
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial
        .builder()
        .id(1L).materialDescription("description").materialSequence(1L).units(1L).pricePerUnit(10D).vendor("vendor")
        .build();

    List<DiyProjectMaterial> diyProjectMaterials = new ArrayList<>();
    diyProjectMaterials.add(diyProjectMaterial);

    ProjectMaterialServiceHelper projectMaterialServiceHelper = new ProjectMaterialServiceHelper();

    // Execute
    Exception expectedException = null;
    List<ProjectDetailMaterialRepresentation> result = null;
    try {
      result = projectMaterialServiceHelper.buildMaterials(diyProjectMaterials);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.get(0).getId() == 1L;
    assert result.get(0).getMaterialDescription().equals("description");
    assert result.get(0).getMaterialSequence() == 1L;
    assert result.get(0).getUnits() == 1L;
    assert result.get(0).getPricePerUnit() == 10D;
    assert result.get(0).getCost() == 10.00D;
    assert result.get(0).getVendor().equals("vendor");
  }

  @Test
  public void testGroupMaterialsByVendors() {
    // Initialize
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation
        .builder()
        .id(1L).materialDescription("description").materialSequence(1L).units(1L).pricePerUnit(10D).vendor("vendor")
        .build();

    List<ProjectDetailMaterialRepresentation> projectDetailMaterialRepresentations = new ArrayList<>();
    projectDetailMaterialRepresentations.add(projectDetailMaterialRepresentation);

    ProjectMaterialServiceHelper projectMaterialServiceHelper = new ProjectMaterialServiceHelper();

    // Execute
    Exception expectedException = null;
    Map<String, List<ProjectDetailMaterialRepresentation>> result = null;
    try {
      result = projectMaterialServiceHelper.groupMaterialsByVendors(projectDetailMaterialRepresentations);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.get("vendor").get(0) == projectDetailMaterialRepresentation;
  }

  @Test
  public void testGroupMaterialsByVendorsOther() {
    // Initialize
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation
        .builder()
        .id(1L).materialDescription("description").materialSequence(1L).units(1L).pricePerUnit(10D)
        .build();

    List<ProjectDetailMaterialRepresentation> projectDetailMaterialRepresentations = new ArrayList<>();
    projectDetailMaterialRepresentations.add(projectDetailMaterialRepresentation);

    ProjectMaterialServiceHelper projectMaterialServiceHelper = new ProjectMaterialServiceHelper();

    // Execute
    Exception expectedException = null;
    Map<String, List<ProjectDetailMaterialRepresentation>> result = null;
    try {
      result = projectMaterialServiceHelper.groupMaterialsByVendors(projectDetailMaterialRepresentations);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.get("Other").get(0) == projectDetailMaterialRepresentation;
  }

  @Test
  public void testCalculateTotalProjectCost() {
    // Initialize
    DiyProjectMaterial diyProjectMaterial1 = DiyProjectMaterial
        .builder()
        .id(1L).materialDescription("description").materialSequence(1L).units(1L).pricePerUnit(10D).vendor("vendor")
        .build();
    DiyProjectMaterial diyProjectMaterial2 = DiyProjectMaterial
        .builder()
        .id(2L).materialDescription("description").materialSequence(1L).units(1L).pricePerUnit(10D).vendor("vendor")
        .build();

    List<DiyProjectMaterial> diyProjectMaterials = new ArrayList<>();
    diyProjectMaterials.add(diyProjectMaterial1);
    diyProjectMaterials.add(diyProjectMaterial2);

    ProjectMaterialServiceHelper projectMaterialServiceHelper = new ProjectMaterialServiceHelper();

    // Execute
    Exception expectedException = null;
    Double result = null;
    try {
      result = projectMaterialServiceHelper.calculateTotalProjectCost(diyProjectMaterials);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result == 20D;
  }

  @Test
  public void testOverBudgetAmount() {
    // Initialize
    ProjectMaterialServiceHelper projectMaterialServiceHelper = new ProjectMaterialServiceHelper();

    // Execute
    Exception expectedException = null;
    Double result = null;
    try {
      result = projectMaterialServiceHelper.overBudgetAmount(200D, 100D);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result == 100D;
  }
}
