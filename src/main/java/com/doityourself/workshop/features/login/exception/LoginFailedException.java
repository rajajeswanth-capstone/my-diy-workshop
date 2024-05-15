package com.doityourself.workshop.features.login.exception;

import com.doityourself.workshop.common.exception.DiyWorkshopException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Login failed exception
 */
@NoArgsConstructor
@Getter
@Setter
public class LoginFailedException extends DiyWorkshopException {
}
