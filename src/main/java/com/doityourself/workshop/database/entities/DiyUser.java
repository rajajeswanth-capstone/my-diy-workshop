package com.doityourself.workshop.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Diy User entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class DiyUser {
  /**
   * Unique identifier for the user
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Name of the user
   */
  @NotBlank(message = "Name is mandatory")
  private String name;

  /**
   * Username of the user
   */
  @NotBlank(message = "User Name is mandatory")
  private String userName;

  /**
   * Password of the user
   */
  @NotBlank(message = "Password is mandatory")
  private String password;
}
