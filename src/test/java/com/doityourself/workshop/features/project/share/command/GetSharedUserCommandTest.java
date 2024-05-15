package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import com.doityourself.workshop.features.user.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetSharedUserCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    UserDao mockUserDao = Mockito.mock(UserDao.class);

    ShareProjectRepresentation shareProjectRepresentation = ShareProjectRepresentation.builder().sharedUserId(1L).build();
    DiyUser diyUser = DiyUser.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_SHARE_PROJECT, shareProjectRepresentation).build();

    GetSharedUserCommand command = new GetSharedUserCommand();
    command.userDao = mockUserDao;

    // Define Mocks
    Mockito.when(mockUserDao.findByUserId(1L)).thenReturn(diyUser);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUserDao, Mockito.times(1)).findByUserId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_USER) == diyUser;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    ShareProjectValidations mockShareProjectValidations = Mockito.mock(ShareProjectValidations.class);

    DiyUser diyUser = DiyUser.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_USER, diyUser).build();

    GetSharedUserCommand command = new GetSharedUserCommand();
    command.validations = mockShareProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockShareProjectValidations).validateSharedDiyUserEntity(diyUser);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockShareProjectValidations, Mockito.times(1)).validateSharedDiyUserEntity(diyUser);

    // Assertions
    assert expectedException == null;
  }
}
