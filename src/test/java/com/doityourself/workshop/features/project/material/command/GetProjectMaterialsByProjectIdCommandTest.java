package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class GetProjectMaterialsByProjectIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMaterialDao mockProjectMaterialDao = Mockito.mock(ProjectMaterialDao.class);

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();

    List<DiyProjectMaterial> diyProjectMaterials = new ArrayList<>();
    diyProjectMaterials.add(diyProjectMaterial);

    List<String> vendors = new ArrayList<>();
    vendors.add("vendor");

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_ID, 1L).build();

    GetProjectMaterialsByProjectIdCommand command = new GetProjectMaterialsByProjectIdCommand();
    command.projectMaterialDao = mockProjectMaterialDao;

    // Define Mocks
    Mockito.when(mockProjectMaterialDao.findProjectMaterialsByProjectId(1L)).thenReturn(diyProjectMaterials);
    Mockito.when(mockProjectMaterialDao.findDistinctProjectVendorsByProjectId(1L)).thenReturn(vendors);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).findProjectMaterialsByProjectId(1L);
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).findDistinctProjectVendorsByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIALS) == diyProjectMaterials;
    assert commandDTO.get(ContextConstants.CONTEXT_VENDORS) == vendors;
  }
}
