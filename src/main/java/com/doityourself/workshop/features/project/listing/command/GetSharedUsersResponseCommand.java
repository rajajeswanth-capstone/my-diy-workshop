package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.user.representation.SharedUserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Get Shared Users Response Command
 */
@Component
public class GetSharedUsersResponseCommand implements ICommand<CommandDTO> {
  /**
   * Process get shared users response. Build response object
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(CommandDTO dto) {
    List<DiyUser> diyUsers = (List<DiyUser>) dto.get(EntityConstants.ENTITY_DIY_USERS);
    List<SharedUserRepresentation> sharedUsers = Optional.ofNullable(diyUsers)
        .map(users ->
            users.stream()
                .map(u ->
                    (SharedUserRepresentation) SharedUserRepresentation.builder()
                        .id(u.getId())
                        .userName(u.getUserName())
                        .name(u.getName())
                        .build()
                ).collect(Collectors.toList())
        ).orElse(null);
    dto.add(ContextConstants.CONTEXT_SHARED_USERS, sharedUsers);
  }
}
