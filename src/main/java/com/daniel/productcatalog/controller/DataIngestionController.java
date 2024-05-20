package com.daniel.productcatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/data-ingestion")
public class DataIngestionController {

  @PostMapping("/upload-csv")
  public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok("CSV file processed successfully");
  }
}
