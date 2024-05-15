package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Get Project Detail Instructions Command
 */
@Component
public class GetProjectDetailInstructionsCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectDetailDao projectDetailDao;

  /**
   * Get Project instructions from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    List<DiyProjectInstruction> diyProjectInstructions = projectDetailDao.findProjectInstructionsByProjectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTIONS, diyProjectInstructions);
  }
}
