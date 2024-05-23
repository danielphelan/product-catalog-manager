package com.daniel.productcatalog.controller;

import com.daniel.productcatalog.service.DataIngestionService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/data-ingestion")
public class DataIngestionController {

  private final DataIngestionService dataIngestionService;

  @Autowired
  public DataIngestionController(DataIngestionService dataIngestionService) {
    this.dataIngestionService = dataIngestionService;
  }

  @PostMapping("/upload-csv")
  public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("File is empty. Please upload a non-empty CSV file.");
    }
    try {
      dataIngestionService.loadCsvData(file.getInputStream());
      return ResponseEntity.ok("CSV file processed successfully");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing CSV file.");
    }

  }
}
