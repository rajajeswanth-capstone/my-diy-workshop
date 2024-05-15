package com.doityourself.workshop.features.signup.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.signup.command.SignupCommand;
import com.doityourself.workshop.features.signup.command.SignupRequestValidationCommand;
import com.doityourself.workshop.features.signup.command.SignupResponseCommand;
import com.doityourself.workshop.features.signup.exception.SignupFailedException;
import com.doityourself.workshop.features.signup.exceptionHandler.SignupGlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Signup Service
 */
@Service
public class SignupService {
  @Autowired
  CommandChain commandChain;

  /**
   * Handle signup
   *
   * @param dto {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  public CommandDTO signup(CommandDTO dto) throws SignupFailedException {
    commandChain.execute(dto,
        Arrays.asList(
            SignupRequestValidationCommand.class,
            SignupCommand.class,
            SignupResponseCommand.class
        ),
        SignupGlobalExceptionHandler.class
    );

    return dto;
  }
}
