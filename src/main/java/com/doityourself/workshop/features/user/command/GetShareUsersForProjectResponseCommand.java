package com.doityourself.workshop.features.user.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.user.representation.SharedUserRepresentation;
import com.doityourself.workshop.features.user.representation.SharedUsersForProjectRepresentation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Get shared users for project command
 */
@Component
public class GetShareUsersForProjectResponseCommand implements ICommand<CommandDTO> {
  /**
   * Process get shared users for users response. Build response object
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(CommandDTO dto) {
    List<DiyUser> diyUsers = (List<DiyUser>) dto.get(EntityConstants.ENTITY_DIY_USERS);
    List<SharedUserRepresentation> sharedUsers = new ArrayList<>();

    diyUsers.forEach(i ->
      sharedUsers.add(
          SharedUserRepresentation.builder()
              .id(i.getId())
              .userName(i.getUserName())
              .name(i.getName())
              .build()
      )
    );

    dto.add(
        ContextConstants.CONTEXT_SHARED_USERS_FOR_PROJECT,
        SharedUsersForProjectRepresentation.builder()
            .projectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID))
            .sharedUsers(sharedUsers)
            .build()
    );
  }
}
