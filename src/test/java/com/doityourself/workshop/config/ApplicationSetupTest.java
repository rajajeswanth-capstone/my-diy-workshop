package com.doityourself.workshop.config;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationSetupTest {
  @Test
  public void testRunCreateDirectory() throws Exception {
    // Initialize
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get("public")).thenReturn(mockProjectPath);

      mockFiles.when(() -> Files.exists(mockProjectPath)).thenReturn(false);
      mockFiles.when(() -> Files.createDirectory(mockProjectPath)).thenReturn(mockProjectPath);

      // Execute
      ApplicationSetup applicationSetup = new ApplicationSetup();
      applicationSetup.run();

      // Verify
      mockFiles.verify(() -> Files.createDirectory(mockProjectPath), Mockito.times(1));
    }
  }

  @Test
  public void testRunCreateLocalDb() throws Exception {
    // Initialize
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class);
         MockedStatic<FileSystemUtils> mockFileSystemUtils = Mockito.mockStatic(FileSystemUtils.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get("public")).thenReturn(mockProjectPath);

      mockFiles.when(() -> Files.exists(mockProjectPath)).thenReturn(true);
      mockFiles.when(() -> Files.createDirectory(mockProjectPath)).thenReturn(mockProjectPath);
      mockFileSystemUtils.when(() -> FileSystemUtils.deleteRecursively(mockProjectPath)).thenReturn(true);
      mockFiles.when(() -> Files.createDirectory(mockProjectPath)).thenReturn(mockProjectPath);

      // Execute
      ApplicationSetup applicationSetup = new ApplicationSetup();
      applicationSetup.localdb = false;
      applicationSetup.run();

      // Verify
      mockFileSystemUtils.verify(() -> FileSystemUtils.deleteRecursively(mockProjectPath), Mockito.times(1));
      mockFiles.verify(() -> Files.createDirectory(mockProjectPath), Mockito.times(1));
    }
  }
}
