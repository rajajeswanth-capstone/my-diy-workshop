package com.doityourself.workshop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Application Setup
 */
@Slf4j
@Component
public class ApplicationSetup implements CommandLineRunner {
  @Value("${local.db}")
  boolean localdb;

  /**
   * Method to run on startup
   *
   * @param args {@link String}...
   * @throws Exception on failure
   */
  @Override
  public void run(String... args) throws Exception {
    log.info("Creating a public folder in the jar location for static content");
    Path path = Paths.get("public");
    if (!Files.exists(path)) {
      Files.createDirectory(path);
    } else if (!localdb) {
      FileSystemUtils.deleteRecursively(path);
      Files.createDirectory(path);
    }
  }
}
