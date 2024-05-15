package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.exception.SaveProjectMaterialFailedException;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Save Project Material Validations
 */
@Component
public class SaveProjectMaterialValidations {
  @Value("${project.save.material.validation.failed}")
  String projectSaveMaterialFailedErrorMessage;

  @Value("${project.save.material.validation.description.required}")
  String projectSaveMaterialDescriptionRequiredErrorMessage;

  @Value("${project.save.material.validation.description.fieldName}")
  String projectSaveMaterialDescriptionFieldName;

  @Value("${project.save.material.validation.units.required}")
  String projectSaveMaterialUnitsRequiredErrorMessage;

  @Value("${project.save.material.validation.units.fieldName}")
  String projectSaveMaterialUnitsFieldName;

  @Value("${project.save.material.validation.pricePerUnit.required}")
  String projectSaveMaterialPricePerUnitRequiredErrorMessage;

  @Value("${project.save.material.validation.pricePerUnit.fieldName}")
  String projectSaveMaterialPricePerUnitFieldName;

  /**
   * Method to validate {@link ProjectDetailMaterialRepresentation}
   *
   * @param material {@link ProjectDetailMaterialRepresentation}
   */
  public void validateSaveMaterialRequest(ProjectDetailMaterialRepresentation material) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateDescription(material, fieldMessages);
    validateUnits(material, fieldMessages);
    validatePricePerUnit(material, fieldMessages);

    if (fieldMessages.size() > 0) {
      SaveProjectMaterialFailedException exception = new SaveProjectMaterialFailedException();
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
      SaveProjectMaterialFailedException exception = new SaveProjectMaterialFailedException();
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
      messages.add(projectSaveMaterialFailedErrorMessage);
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
      messages.put(projectSaveMaterialDescriptionFieldName, projectSaveMaterialDescriptionRequiredErrorMessage);
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
      messages.put(projectSaveMaterialUnitsFieldName, projectSaveMaterialUnitsRequiredErrorMessage);
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
      messages.put(projectSaveMaterialPricePerUnitFieldName, projectSaveMaterialPricePerUnitRequiredErrorMessage);
    }
  }
}
