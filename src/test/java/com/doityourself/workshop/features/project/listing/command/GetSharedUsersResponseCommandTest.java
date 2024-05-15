package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.user.representation.SharedUserRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetSharedUsersResponseCommandTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testProcess() {
    // Initialize
    List<DiyUser> diyUsers = new ArrayList<>();
    diyUsers.add(
        DiyUser
            .builder()
            .id(1L).userName("username").name("name")
            .build()
    );

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_USERS, diyUsers)
        .build();

    GetSharedUsersResponseCommand command = new GetSharedUsersResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert ((List<SharedUserRepresentation>) commandDTO.get(ContextConstants.CONTEXT_SHARED_USERS)).get(0).getId() == 1L;
    assert ((List<SharedUserRepresentation>) commandDTO.get(ContextConstants.CONTEXT_SHARED_USERS)).get(0).getUserName().equals("username");
    assert ((List<SharedUserRepresentation>) commandDTO.get(ContextConstants.CONTEXT_SHARED_USERS)).get(0).getName().equals("name");
  }
}
