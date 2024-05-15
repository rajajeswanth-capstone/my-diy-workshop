package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.exception.UpdateProjectMaterialFailedException;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Update Project Material Validations
 */
@Component
public class UpdateProjectMaterialValidations {
  @Value("${project.update.material.validation.failed}")
  String projectUpdateMaterialFailedErrorMessage;

  @Value("${project.update.material.validation.description.required}")
  String projectUpdateMaterialDescriptionRequiredErrorMessage;

  @Value("${project.update.material.validation.description.fieldName}")
  String projectUpdateMaterialDescriptionFieldName;

  @Value("${project.update.material.validation.id.required}")
  String projectUpdateMaterialIdRequiredErrorMessage;

  @Value("${project.update.material.validation.id.fieldName}")
  String projectUpdateMaterialIdFieldName;

  @Value("${project.update.material.validation.units.required}")
  String projectUpdateMaterialUnitsRequiredErrorMessage;

  @Value("${project.update.material.validation.units.fieldName}")
  String projectUpdateSaveMaterialUnitsFieldName;

  @Value("${project.update.material.validation.pricePerUnit.required}")
  String projectUpdateMaterialPricePerUnitRequiredErrorMessage;

  @Value("${project.update.material.validation.pricePerUnit.fieldName}")
  String projectUpdateMaterialPricePerUnitFieldName;

  /**
   * Method to validate {@link ProjectDetailMaterialRepresentation}
   *
   * @param material {@link ProjectDetailMaterialRepresentation}
   */
  public void validateUpdateMaterialRequest(ProjectDetailMaterialRepresentation material) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateId(material, fieldMessages);
    validateDescription(material, fieldMessages);
    validateUnits(material, fieldMessages);
    validatePricePerUnit(material, fieldMessages);

    if (fieldMessages.size() > 0) {
      UpdateProjectMaterialFailedException exception = new UpdateProjectMaterialFailedException();
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
      UpdateProjectMaterialFailedException exception = new UpdateProjectMaterialFailedException();
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
      messages.add(projectUpdateMaterialFailedErrorMessage);
    }
  }

  /**
   * Method to validate description
   *
   * @param material {@link ProjectDetailMaterialRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateDescription(ProjectDetailMaterialRepresentation material, Map<String, String> messages) {
    if (StringUtility.isEmpty(material.getMaterialDescription())) {
      messages.put(projectUpdateMaterialDescriptionFieldName, projectUpdateMaterialDescriptionRequiredErrorMessage);
    }
  }

  /**
   * Method to validate id
   *
   * @param material {@link ProjectDetailMaterialRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateId(ProjectDetailMaterialRepresentation material, Map<String, String> messages) {
    if (Objects.isNull(material.getId())) {
      messages.put(projectUpdateMaterialIdFieldName, projectUpdateMaterialIdRequiredErrorMessage);
    }
  }

  /**
   * Method to validate units
   *
   * @param material {@link ProjectDetailMaterialRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateUnits(ProjectDetailMaterialRepresentation material, Map<String, String> messages) {
    if (Objects.isNull(material.getUnits())) {
      messages.put(projectUpdateSaveMaterialUnitsFieldName, projectUpdateMaterialUnitsRequiredErrorMessage);
    }
  }

  /**
   * Method to validate price per unit
   *
   * @param material {@link ProjectDetailMaterialRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validatePricePerUnit(ProjectDetailMaterialRepresentation material, Map<String, String> messages) {
    if (Objects.isNull(material.getPricePerUnit())) {
      messages.put(projectUpdateMaterialPricePerUnitFieldName, projectUpdateMaterialPricePerUnitRequiredErrorMessage);
    }
  }
}
