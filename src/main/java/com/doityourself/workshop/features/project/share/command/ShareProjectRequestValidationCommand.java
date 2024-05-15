package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Share project request validation command
 */
@Component
public class ShareProjectRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  ShareProjectValidations validations;

  /**
   * Share project request validation
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) throws ProjectSaveFailedException {
    validations.validateShareProjectRequest((ShareProjectRepresentation) dto.get(ContextConstants.CONTEXT_SHARE_PROJECT));
  }
}
