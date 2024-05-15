package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.exception.GetProjectMaterialFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Get Project Material Validations
 */
@Component
public class GetProjectMaterialValidations {
  @Value("${project.material.validation.failed}")
  String projectMaterialFailedErrorMessage;

  @Value("${project.material.validation.id.required}")
  String projectMaterialIdRequiredErrorMessage;

  @Value("${project.material.validation.id.fieldName}")
  String projectMaterialIdFieldName;

  /**
   * Method to validate project instruction id
   *
   * @param id {@link Long}
   */
  public void validateGetProjectMaterialRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectMaterialId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      GetProjectMaterialFailedException exception = new GetProjectMaterialFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate entity
   *
   * @param diyProjectMaterial {@link DiyProjectMaterial}
   */
  public void validateDiyProjectMaterialEntity(DiyProjectMaterial diyProjectMaterial) {
    List<String> messages = new ArrayList<>();
    validateNullDiyProjectMaterial(diyProjectMaterial, messages);

    if (messages.size() > 0) {
      GetProjectMaterialFailedException exception = new GetProjectMaterialFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyProjectMaterial} entity
   *
   * @param diyProjectMaterial {@link DiyProjectMaterial}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyProjectMaterial(DiyProjectMaterial diyProjectMaterial, List<String> messages) {
    if (Objects.isNull(diyProjectMaterial)) {
      messages.add(projectMaterialFailedErrorMessage);
    }
  }

  /**
   * Method to validate project material id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectMaterialId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectMaterialIdFieldName, projectMaterialIdRequiredErrorMessage);
    }
  }
}
