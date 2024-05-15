package com.doityourself.workshop.features.user.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.user.representation.SharedUserRepresentation;
import com.doityourself.workshop.features.user.representation.SharedUsersForProjectRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetShareUsersForProjectResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyUser diyUser = DiyUser.builder().id(1L).userName("username").name("name").build();
    List<DiyUser> diyUsers = new ArrayList<>();
    diyUsers.add(diyUser);

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_USERS, diyUsers)
        .add(ContextConstants.CONTEXT_PROJECT_ID, 1L)
        .build();

    GetShareUsersForProjectResponseCommand command = new GetShareUsersForProjectResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    SharedUsersForProjectRepresentation sharedUsersForProjectRepresentation = (SharedUsersForProjectRepresentation) commandDTO.get(ContextConstants.CONTEXT_SHARED_USERS_FOR_PROJECT);
    SharedUserRepresentation sharedUserRepresentation = sharedUsersForProjectRepresentation.getSharedUsers().get(0);

    assert sharedUsersForProjectRepresentation.getProjectId() == 1L;
    assert sharedUserRepresentation.getId() == 1L;
    assert sharedUserRepresentation.getUserName().equals("username");
    assert sharedUserRepresentation.getName().equals("name");
  }
}
