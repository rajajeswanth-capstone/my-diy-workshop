package com.doityourself.workshop.features.user.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.signup.exception.SignupFailedException;
import com.doityourself.workshop.features.user.command.GetShareUsersForProjectRequestValidationCommand;
import com.doityourself.workshop.features.user.command.GetShareUsersForProjectResponseCommand;
import com.doityourself.workshop.features.user.command.GetSharedUsersCommand;
import com.doityourself.workshop.features.user.exceptionHandler.GetSharedUsersForProjectGlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * User Service
 */
@Service
public class UserService {
  @Autowired
  CommandChain commandChain;

  /**
   * Get shared users for project
   *
   * @param dto {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  public CommandDTO getSharedUsersForProject(CommandDTO dto) throws SignupFailedException {
    commandChain.execute(dto,
        Arrays.asList(
            GetShareUsersForProjectRequestValidationCommand.class,
            GetSharedUsersCommand.class,
            GetShareUsersForProjectResponseCommand.class
        ),
        GetSharedUsersForProjectGlobalExceptionHandler.class
    );

    return dto;
  }
}
