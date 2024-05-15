package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.helper.ProjectMaterialServiceHelper;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetProjectMaterialsResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMaterialServiceHelper mockProjectMaterialServiceHelper = Mockito.mock(ProjectMaterialServiceHelper.class);

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    DiyProject diyProject = DiyProject
        .builder()
        .id(1L).title("title").description("description").budget(10D)
        .build();

    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

    List<DiyProjectMaterial> diyProjectMaterials = new ArrayList<>();
    diyProjectMaterials.add(diyProjectMaterial);

    List<ProjectDetailMaterialRepresentation> projectDetailMaterialRepresentations = new ArrayList<>();
    projectDetailMaterialRepresentations.add(projectDetailMaterialRepresentation);

    Map<String, List<ProjectDetailMaterialRepresentation>> responseMaterialsByVendor = new HashMap<>();
    responseMaterialsByVendor.put("Other", projectDetailMaterialRepresentations);

    List<String> vendors = new ArrayList<>();
    vendors.add("vendor");

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(EntityConstants.ENTITY_DIY_PROJECT_MATERIALS, diyProjectMaterials)
        .add(ContextConstants.CONTEXT_VENDORS, vendors)
        .build();

    GetProjectMaterialsResponseCommand command = new GetProjectMaterialsResponseCommand();
    command.projectMaterialServiceHelper = mockProjectMaterialServiceHelper;

    // Define Mocks
    Mockito.when(mockProjectMaterialServiceHelper.buildMaterials(diyProjectMaterials)).thenReturn(projectDetailMaterialRepresentations);
    Mockito.when(mockProjectMaterialServiceHelper.calculateTotalProjectCost(diyProjectMaterials)).thenReturn(10D);
    Mockito.when(mockProjectMaterialServiceHelper.overBudgetAmount(10D, 10D)).thenReturn(5D);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMaterialServiceHelper, Mockito.times(1)).buildMaterials(diyProjectMaterials);
    Mockito.verify(mockProjectMaterialServiceHelper, Mockito.times(1)).calculateTotalProjectCost(diyProjectMaterials);
    Mockito.verify(mockProjectMaterialServiceHelper, Mockito.times(1)).overBudgetAmount(10D, 10D);

    // Assertions
    assert expectedException == null;
    ProjectDetailRepresentation projectDetailRepresentation = (ProjectDetailRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_DETAIL);
    assert projectDetailRepresentation.getId() == 1L;
    assert projectDetailRepresentation.getTitle().equals("title");
    assert projectDetailRepresentation.getDescription().equals("description");
    assert projectDetailRepresentation.getBudget() == 10D;
    assert projectDetailRepresentation.getMaterials().get(0) == projectDetailMaterialRepresentation;
    assert projectDetailRepresentation.getTotalProjectCost() == 10D;
    assert projectDetailRepresentation.getOverBudget();
    assert projectDetailRepresentation.getOverBudgetAmount() == 5D;
    assert projectDetailRepresentation.getVendors().get(0).equals("vendor");
  }
}
