package com.doityourself.workshop.common.utility;

import com.doityourself.workshop.common.constants.FileConstants;
import com.doityourself.workshop.common.exception.DiyWorkshopException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtilityTest {
  @Test
  public void testCreateProjectFoldersNoFolder() {
    // Initialize
    long projectId = 1L;
    Path mockProjectPath = Mockito.mock(Path.class);
    Path mockLocalFolderPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId)).thenReturn(mockProjectPath);
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId, FileConstants.LOCAL_FOLDER_NAME)).thenReturn(mockLocalFolderPath);

      mockFiles.when(() -> Files.exists(mockProjectPath)).thenReturn(false);
      mockFiles.when(() -> Files.exists(mockLocalFolderPath)).thenReturn(false);

      // Execute
      FileUtility.createProjectFolders(projectId);

      // Verify
      mockFiles.verify(() -> Files.createDirectory(mockProjectPath), Mockito.times(1));
      mockFiles.verify(() -> Files.createDirectory(mockLocalFolderPath), Mockito.times(1));
    }
  }

  @Test
  public void testCreateProjectFoldersHasFolder() {
    // Initialize
    long projectId = 1L;
    Path mockProjectPath = Mockito.mock(Path.class);
    Path mockLocalFolderPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId)).thenReturn(mockProjectPath);
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId, FileConstants.LOCAL_FOLDER_NAME)).thenReturn(mockLocalFolderPath);

      mockFiles.when(() -> Files.exists(mockProjectPath)).thenReturn(true);
      mockFiles.when(() -> Files.exists(mockLocalFolderPath)).thenReturn(true);

      // Execute
      FileUtility.createProjectFolders(projectId);

      // Verify
      mockFiles.verify(() -> Files.createDirectory(mockProjectPath), Mockito.times(0));
      mockFiles.verify(() -> Files.createDirectory(mockLocalFolderPath), Mockito.times(0));
    }
  }

  @Test
  public void testCreateProjectFoldersException() {
    // Initialize
    long projectId = 1L;
    Path mockProjectPath = Mockito.mock(Path.class);
    Path mockLocalFolderPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId)).thenReturn(mockProjectPath);
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId, FileConstants.LOCAL_FOLDER_NAME)).thenReturn(mockLocalFolderPath);

      mockFiles.when(() -> Files.exists(mockProjectPath)).thenReturn(false);
      mockFiles.when(() -> Files.exists(mockLocalFolderPath)).thenReturn(false);

      mockFiles.when(() -> Files.createDirectory(mockProjectPath)).thenThrow(new IOException("something failed"));

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.createProjectFolders(projectId);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      mockFiles.verify(() -> Files.createDirectory(mockLocalFolderPath), Mockito.times(0));

      // Assertions
      assert expectedException instanceof DiyWorkshopException;
      assert ((DiyWorkshopException) expectedException).getMessages().get(0).equals("something failed");
    }
  }

  @Test
  public void testDeleteProjectFolder() {
    // Initialize
    long projectId = 1L;
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<FileSystemUtils> mockFileSystemUtils = Mockito.mockStatic(FileSystemUtils.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId)).thenReturn(mockProjectPath);

      mockFileSystemUtils.when(() -> FileSystemUtils.deleteRecursively(mockProjectPath)).thenReturn(true);

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.deleteProjectFolder(projectId);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
    }
  }

  @Test
  public void testDeleteProjectFolderException() {
    // Initialize
    long projectId = 1L;
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<FileSystemUtils> mockFileSystemUtils = Mockito.mockStatic(FileSystemUtils.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId)).thenReturn(mockProjectPath);

      mockFileSystemUtils.when(() -> FileSystemUtils.deleteRecursively(mockProjectPath)).thenThrow(new IOException("something failed"));

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.deleteProjectFolder(projectId);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException instanceof DiyWorkshopException;
      assert ((DiyWorkshopException) expectedException).getMessages().get(0).equals("something failed");
    }
  }

  @Test
  public void testIsProjectFolderDeleted() {
    // Initialize
    long projectId = 1L;
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId)).thenReturn(mockProjectPath);

      mockFiles.when(() -> Files.exists(mockProjectPath)).thenReturn(true);

      // Execute
      Exception expectedException = null;
      Boolean result = null;
      try {
        result = FileUtility.isProjectFolderDeleted(projectId);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
      assert !result;
    }
  }

  @Test
  public void testBuildLocalResourceFilePath() {
    // Initialize
    Long localResourceId = 1L;
    Long projectId = 1L;
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class)) {
      // Define Mocks
      Mockito.when(mockFile.getOriginalFilename()).thenReturn("testfile.txt");
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME,
          FileConstants.PROJECT_FOLDER_PREFIX + projectId,
          FileConstants.LOCAL_FOLDER_NAME,
          FileConstants.LOCAL_FILE_PREFIX + localResourceId + "_testfile.txt")).thenReturn(mockProjectPath);

      Mockito.when(mockProjectPath.toString()).thenReturn("test");

      // Execute
      Exception expectedException = null;
      String result = null;
      try {
        result = FileUtility.buildLocalResourceFilePath(localResourceId, projectId, mockFile);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
      assert result.equals("test");
    }
  }

  @Test
  public void testBuildProjectImageFilePath() {
    // Initialize
    Long projectId = 1L;
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class)) {
      // Define Mocks
      Mockito.when(mockFile.getOriginalFilename()).thenReturn("testfile.txt");
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME,
          FileConstants.PROJECT_FOLDER_PREFIX + projectId,
          FileConstants.LOCAL_FOLDER_NAME,
          FileConstants.PROJECT_IMAGE_FILE_PREFIX + projectId + "_testfile.txt")).thenReturn(mockProjectPath);

      Mockito.when(mockProjectPath.toString()).thenReturn("test");

      // Execute
      Exception expectedException = null;
      String result = null;
      try {
        result = FileUtility.buildProjectImageFilePath(projectId, mockFile);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
      assert result.equals("test");
    }
  }

  @Test
  public void testBuildFileLink() {
    // Initialize
    Long localResourceId = 1L;
    Long projectId = 1L;
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);

    // Define Mocks
    Mockito.when(mockFile.getOriginalFilename()).thenReturn("testfile.txt");

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = FileUtility.buildFileLink(localResourceId, projectId, mockFile);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.equals(FileConstants.FILE_SEPARATOR + FileConstants.PROJECT_FOLDER_PREFIX + projectId +
        FileConstants.FILE_SEPARATOR + FileConstants.LOCAL_FOLDER_NAME + FileConstants.FILE_SEPARATOR +
        FileConstants.LOCAL_FILE_PREFIX + localResourceId + "_testfile.txt");
  }

  @Test
  public void testBuildProjectImageFileLink() {
    // Initialize
    Long projectId = 1L;
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);

    // Define Mocks
    Mockito.when(mockFile.getOriginalFilename()).thenReturn("testfile.txt");

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = FileUtility.buildProjectImageFileLink(projectId, mockFile);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.equals(FileConstants.FILE_SEPARATOR + FileConstants.PROJECT_FOLDER_PREFIX + projectId +
        FileConstants.FILE_SEPARATOR + FileConstants.LOCAL_FOLDER_NAME + FileConstants.FILE_SEPARATOR +
        FileConstants.PROJECT_IMAGE_FILE_PREFIX + projectId + "_testfile.txt");
  }

  @Test
  public void testBuildDefaultProjectImageFileLink() {
    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = FileUtility.buildDefaultProjectImageFileLink();
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.equals("/images/default-project.jpg");
  }

  @Test
  public void testBuildFileName() {
    // Initialize
    Long fileId = 1L;

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = FileUtility.buildFileName(fileId, "testfile.txt");
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.equals(FileConstants.LOCAL_FILE_PREFIX + fileId + "_testfile.txt");
  }

  @Test
  public void testOpenFile() throws IOException, URISyntaxException {
    // Initialize
    Long projectId = 1L;
    Path mockProjectPath = Mockito.mock(Path.class);
    Desktop mockDesktop = Mockito.mock(Desktop.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get("/location", FileConstants.PUBLIC_FOLDER_NAME,
          FileConstants.PROJECT_FOLDER_PREFIX + projectId,
          FileConstants.LOCAL_FOLDER_NAME, "testfile.txt")).thenReturn(mockProjectPath);

      Mockito.when(mockProjectPath.toUri()).thenReturn(new URI("/test"));

      Mockito.doNothing().when(mockDesktop).open(Mockito.any());

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.openFile(mockDesktop, projectId, "testfile.txt", "/location");
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException != null;
    }
  }

  @Test
  public void testDeleteLocalResourceByLink() {
    // Initialize
    String link = "/link";
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME + link)).thenReturn(mockProjectPath);

      mockFiles.when(() -> Files.deleteIfExists(mockProjectPath)).thenReturn(true);

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.deleteLocalResourceByLink(link);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
    }
  }

  @Test
  public void testDeleteLocalResourceByLinkException() {
    // Initialize
    String link = "/link";
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME + link)).thenReturn(mockProjectPath);

      mockFiles.when(() -> Files.deleteIfExists(mockProjectPath)).thenThrow(new IOException("something failed"));

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.deleteLocalResourceByLink(link);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException instanceof DiyWorkshopException;
      assert ((DiyWorkshopException) expectedException).getMessages().get(0).equals("something failed");
    }
  }

  @Test
  public void testSaveLocalResource() throws IOException {
    // Initialize
    String filePath = "/link";
    Path mockProjectPath = Mockito.mock(Path.class);
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    InputStream mockInputStream = Mockito.mock(InputStream.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(filePath)).thenReturn(mockProjectPath);
      mockFiles.when(() -> Files.copy(mockInputStream, mockProjectPath, StandardCopyOption.REPLACE_EXISTING)).thenReturn(1L);
      Mockito.when(mockFile.getInputStream()).thenReturn(mockInputStream);

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.saveLocalResource(mockFile, filePath);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
    }
  }

  @Test
  public void testSaveLocalResourceException() throws IOException {
    // Initialize
    String filePath = "/link";
    Path mockProjectPath = Mockito.mock(Path.class);
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    InputStream mockInputStream = Mockito.mock(InputStream.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(filePath)).thenReturn(mockProjectPath);
      mockFiles.when(() -> Files.copy(mockInputStream, mockProjectPath, StandardCopyOption.REPLACE_EXISTING)).thenThrow(new IOException("something failed"));
      Mockito.when(mockFile.getInputStream()).thenReturn(mockInputStream);

      // Execute
      Exception expectedException = null;
      try {
        FileUtility.saveLocalResource(mockFile, filePath);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException instanceof DiyWorkshopException;
      assert ((DiyWorkshopException) expectedException).getMessages().get(0).equals("something failed");
    }
  }

  @Test
  public void testGetFileBytes() {
    // Initialize
    String filePath = "/link";
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(filePath)).thenReturn(mockProjectPath);
      mockFiles.when(() -> Files.readAllBytes(mockProjectPath)).thenReturn("test".getBytes());

      // Execute
      byte[] result = null;
      Exception expectedException = null;
      try {
        result = FileUtility.getFileBytes(filePath);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
      assert new String(result).equals("test");
    }
  }

  @Test
  public void testGetFileBytesException() {
    // Initialize
    String filePath = "/link";
    Path mockProjectPath = Mockito.mock(Path.class);

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(filePath)).thenReturn(mockProjectPath);
      mockFiles.when(() -> Files.readAllBytes(mockProjectPath)).thenThrow(new IOException("something failed"));

      // Execute
      byte[] result = null;
      Exception expectedException = null;
      try {
        result = FileUtility.getFileBytes(filePath);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert result == null;
      assert expectedException instanceof DiyWorkshopException;
      assert ((DiyWorkshopException) expectedException).getMessages().get(0).equals("something failed");
    }
  }

  @Test
  public void testIsProjectFoldersCreated() {
    // Initialize
    Path mockProjectPath = Mockito.mock(Path.class);
    Path mockProjectImagesPath = Mockito.mock(Path.class);
    Long projectId = 1L;

    try (MockedStatic<Paths> mockPaths = Mockito.mockStatic(Paths.class);
         MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
      // Define Mocks
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId)).thenReturn(mockProjectPath);
      mockPaths.when(() -> Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId, FileConstants.LOCAL_FOLDER_NAME)).thenReturn(mockProjectImagesPath);
      mockFiles.when(() -> Files.exists(mockProjectPath)).thenReturn(true);
      mockFiles.when(() -> Files.exists(mockProjectImagesPath)).thenReturn(true);

      // Execute
      Boolean result = null;
      Exception expectedException = null;
      try {
        result = FileUtility.isProjectFoldersCreated(projectId);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
      assert result;
    }
  }
}
