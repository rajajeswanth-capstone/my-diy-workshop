package com.doityourself.workshop.features.user.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.user.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class GetSharedUsersCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    UserDao mockUserDao = Mockito.mock(UserDao.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation
        .builder().userName("username").name("name").build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGGED_IN_USER, loggedInUserRepresentation).build();

    DiyUser responseDiyUser = DiyUser.builder().id(1L).build();
    List<DiyUser> responseDiyUsers = new ArrayList<>();
    responseDiyUsers.add(responseDiyUser);

    GetSharedUsersCommand command = new GetSharedUsersCommand();
    command.userDao = mockUserDao;

    // Define Mocks
    Mockito.when(mockUserDao.getSharedUsers("username")).thenReturn(responseDiyUsers);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUserDao, Mockito.times(1)).getSharedUsers("username");

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_USERS) == responseDiyUsers;
  }
}
