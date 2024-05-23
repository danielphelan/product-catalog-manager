package com.daniel.productcatalog.controller;

import com.daniel.productcatalog.service.impl.DataIngestionServiceImpl;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/data-ingestion")
public class DataIngestionController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final DataIngestionServiceImpl dataIngestionService;

  public DataIngestionController(DataIngestionServiceImpl dataIngestionService) {
    this.dataIngestionService = dataIngestionService;
  }

  @PostMapping("/upload-csv")
  public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
    logger.info("Received a request to upload a CSV file");

    if (file.isEmpty()) {
      logger.warn("Upload attempt with an empty file");
      return ResponseEntity.badRequest().body("File is empty. Please upload a non-empty CSV file.");
    }
    if (!Objects.equals(file.getContentType(), "text/csv")) {
      logger.warn("Upload attempt with invalid file type: {}", file.getContentType());
      return ResponseEntity.badRequest().body("Invalid file type. Please upload a CSV file.");
    }
    try {
      logger.info("CSV Valid - Starting processing for csv upload");
      dataIngestionService.processCsvData(file.getInputStream());
      return ResponseEntity.ok("CSV file processed successfully");
    } catch (IOException e) {
      logger.error("Error processing CSV file", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing CSV file.");
    }
  }
}
