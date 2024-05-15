package com.doityourself.workshop.features.login.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.login.command.LoginCommand;
import com.doityourself.workshop.features.login.command.LoginRequestValidationCommand;
import com.doityourself.workshop.features.login.command.LoginResponseCommand;
import com.doityourself.workshop.features.login.exceptionHandler.LoginGlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Login Service
 */
@Service
public class LoginService {
  @Autowired
  CommandChain commandChain;

  /**
   * Handle login
   *
   * @param dto {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  public CommandDTO login(CommandDTO dto) {
    commandChain.execute(dto,
        Arrays.asList(
            LoginRequestValidationCommand.class,
            LoginCommand.class,
            LoginResponseCommand.class
        ),
        LoginGlobalExceptionHandler.class
    );

    return dto;
  }
}
