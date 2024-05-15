package com.doityourself.workshop.features.home.service;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.listing.service.ProjectListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Home Service
 */
@Service
public class HomeService {
  @Autowired
  public ProjectListingService projectListingService;

  /**
   * Method to get projects for current user
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  public CommandDTO getProjects(CommandDTO commandDTO) {
    return projectListingService.getProjects(commandDTO);
  }
}
