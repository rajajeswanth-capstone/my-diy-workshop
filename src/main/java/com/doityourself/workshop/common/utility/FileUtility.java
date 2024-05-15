package com.doityourself.workshop.common.utility;

import com.doityourself.workshop.common.constants.FileConstants;
import com.doityourself.workshop.common.exception.DiyWorkshopException;
import com.doityourself.workshop.features.project.media.exception.GetProjectLocalResourceFailedException;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

/**
 * File Utility
 */
public class FileUtility {
  /**
   * Method to create project folder
   *
   * @param projectId {@link Long}
   */
  public static void createProjectFolders(Long projectId) {
    try {
      Path projectPath = Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId);
      Path localFolderPath = Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId, FileConstants.LOCAL_FOLDER_NAME);

      if (!Files.exists(projectPath)) {
        Files.createDirectory(projectPath);
      }
      if (!Files.exists(localFolderPath)) {
        Files.createDirectory(localFolderPath);
      }
    } catch (IOException exception) {
      DiyWorkshopException throwException = new DiyWorkshopException();
      throwException.setMessages(Collections.singletonList(exception.getMessage()));
      throw throwException;
    }
  }

  /**
   * Method to delete project folder
   *
   * @param projectId {@link Long}
   */
  public static void deleteProjectFolder(Long projectId) {
    try {
      Path path = Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId);
      FileSystemUtils.deleteRecursively(path);
    } catch (IOException exception) {
      DiyWorkshopException throwException = new DiyWorkshopException();
      throwException.setMessages(Collections.singletonList(exception.getMessage()));
      throw throwException;
    }
  }

  /**
   * Method to check if project folder is deleted
   *
   * @param projectId {@link Long}
   * @return boolean
   */
  public static boolean isProjectFolderDeleted(Long projectId) {
    String folderName = FileConstants.PROJECT_FOLDER_PREFIX + projectId;
    Path path = Paths.get(FileConstants.PUBLIC_FOLDER_NAME, folderName);
    return !Files.exists(path);
  }

  /**
   * Method to build local resource file path
   *
   * @param localResourceId {@link Long}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  public static String buildLocalResourceFilePath(Long localResourceId, Long projectId, MultipartFile file) {
    return Paths.get(FileConstants.PUBLIC_FOLDER_NAME,
        FileConstants.PROJECT_FOLDER_PREFIX + projectId,
        FileConstants.LOCAL_FOLDER_NAME,
        FileConstants.LOCAL_FILE_PREFIX + localResourceId + "_" + file.getOriginalFilename()).toString();
  }

  /**
   * Method to build local resource file path
   *
   * @param projectId {@link Long}
   * @return {@link String}
   */
  public static String buildProjectImageFilePath(Long projectId, MultipartFile file) {
    return Paths.get(FileConstants.PUBLIC_FOLDER_NAME,
        FileConstants.PROJECT_FOLDER_PREFIX + projectId,
        FileConstants.LOCAL_FOLDER_NAME,
        FileConstants.PROJECT_IMAGE_FILE_PREFIX + projectId + "_" + file.getOriginalFilename()).toString();
  }

  /**
   * Method to build file link
   *
   * @param fileId {@link Long}
   * @param projectId {@link Long}
   * @param file {@link MultipartFile}
   * @return {@link String}
   */
  public static String buildFileLink(Long fileId, Long projectId, MultipartFile file) {
    String projectFolderName = FileConstants.FILE_SEPARATOR + FileConstants.PROJECT_FOLDER_PREFIX + projectId;
    String localFolderName = projectFolderName + FileConstants.FILE_SEPARATOR + FileConstants.LOCAL_FOLDER_NAME;
    return localFolderName + FileConstants.FILE_SEPARATOR + FileConstants.LOCAL_FILE_PREFIX + fileId + "_" + file.getOriginalFilename();
  }

  /**
   * Method to build file link
   *
   * @param projectId {@link Long}
   * @param file {@link MultipartFile}
   * @return {@link String}
   */
  public static String buildProjectImageFileLink(Long projectId, MultipartFile file) {
    String projectFolderName = FileConstants.FILE_SEPARATOR + FileConstants.PROJECT_FOLDER_PREFIX + projectId;
    String localFolderName = projectFolderName + FileConstants.FILE_SEPARATOR + FileConstants.LOCAL_FOLDER_NAME;
    return localFolderName + FileConstants.FILE_SEPARATOR + FileConstants.PROJECT_IMAGE_FILE_PREFIX + projectId + "_" + file.getOriginalFilename();
  }

  /**
   * Method to build default project image file link
   *
   * @return {@link String}
   */
  public static String buildDefaultProjectImageFileLink() {
    return "/images/default-project.jpg";
  }

  /**
   * Method to build file name
   *
   * @param fileId {@link Long}
   * @param originalFileName {@link String}
   * @return {@link String}
   */
  public static String buildFileName(Long fileId, String originalFileName) {
    return FileConstants.LOCAL_FILE_PREFIX + fileId + "_" + originalFileName;
  }

  /**
   * Method to open file
   *
   * @param desktop {@link Desktop}
   * @param projectId {@link Long}
   * @param fileName {@link String}
   * @param jarLocation {@link String}
   */
  public static void openFile(Desktop desktop, Long projectId, String fileName, String jarLocation) {
    Path fullPath = Paths.get(jarLocation, FileConstants.PUBLIC_FOLDER_NAME,
        FileConstants.PROJECT_FOLDER_PREFIX + projectId,
        FileConstants.LOCAL_FOLDER_NAME, fileName);
    try {
      desktop.open(new File(fullPath.toUri()));
    } catch (Exception e) {
      GetProjectLocalResourceFailedException exception = new GetProjectLocalResourceFailedException();
      exception.setMessages(Collections.singletonList(e.getMessage()));
      throw exception;
    }
  }

  /**
   * Method to delete local resource by link
   *
   * @param link {@link String}
   */
  public static void deleteLocalResourceByLink(String link) {
    try {
      Path path = Paths.get(FileConstants.PUBLIC_FOLDER_NAME + link);
      Files.deleteIfExists(path);
    } catch (IOException exception) {
      DiyWorkshopException throwException = new DiyWorkshopException();
      throwException.setMessages(Collections.singletonList(exception.getMessage()));
      throw throwException;
    }
  }

  /**
   * Method to save local resource
   *
   * @param file {@link MultipartFile}
   * @param filePath {@link String}
   */
  public static void saveLocalResource(MultipartFile file, String filePath) {
    try {
      Path path = Paths.get(filePath);
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException exception) {
      DiyWorkshopException throwException = new DiyWorkshopException();
      throwException.setMessages(Collections.singletonList(exception.getMessage()));
      throw throwException;
    }
  }

  /**
   * Method to read file bytes
   *
   * @param filePath {@link String}
   * @return byte[]
   */
  public static byte[] getFileBytes(String filePath) {
    try {
      Path path = Paths.get(filePath);
      return Files.readAllBytes(path);
    } catch (IOException exception) {
      DiyWorkshopException throwException = new DiyWorkshopException();
      throwException.setMessages(Collections.singletonList(exception.getMessage()));
      throw throwException;
    }
  }

  /**
   * Method to check if project folder is created
   *
   * @param projectId {@link Long}
   * @return boolean
   */
  public static boolean isProjectFoldersCreated(Long projectId) {
    Path projectPath = Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId);
    Path imagesPath = Paths.get(FileConstants.PUBLIC_FOLDER_NAME, FileConstants.PROJECT_FOLDER_PREFIX + projectId, FileConstants.LOCAL_FOLDER_NAME);

    return Files.exists(projectPath) && Files.exists(imagesPath);
  }
}
