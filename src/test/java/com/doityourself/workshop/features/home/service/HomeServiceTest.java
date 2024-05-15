package com.doityourself.workshop.features.home.service;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.listing.service.ProjectListingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HomeServiceTest {
  @Test
  public void testGetProjects() {
    // Create a Mock using Mockito
    ProjectListingService projectListingServiceMock = Mockito.mock(ProjectListingService.class);

    // Set the Mock in the service
    HomeService homeService = new HomeService();
    homeService.projectListingService = projectListingServiceMock;

    // Define Mock behavior
    Mockito.when(
        projectListingServiceMock.getProjects(Mockito.any())
    ).thenReturn(
        CommandDTO.builder().add("a", "b").build()
    );

    // Execute Method
    CommandDTO response = homeService.getProjects(CommandDTO.builder().build());

    // Assert response
    assert response.get("a").equals("b");
  }
}
