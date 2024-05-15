package com.doityourself.workshop.features.signup.exception;

import com.doityourself.workshop.common.exception.DiyWorkshopException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Signup failed exception
 */
@NoArgsConstructor
@Getter
@Setter
public class SignupFailedException extends DiyWorkshopException {
}
