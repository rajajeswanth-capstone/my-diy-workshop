package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Instruction By Id Command
 */
@Component
public class DeleteProjectInstructionByInstructionIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectDetailDao projectDetailDao;

  /**
   * Delete Project instruction by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    projectDetailDao.deleteProjectInstructionByInstructionId((Long) dto.get(ContextConstants.CONTEXT_INSTRUCTION_ID));
  }
}
