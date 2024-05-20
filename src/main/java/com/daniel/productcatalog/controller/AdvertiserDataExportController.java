package com.daniel.productcatalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/export")
public class AdvertiserDataExportController {

  @GetMapping("/advertisers")
  public ResponseEntity<String> exportAdvertisersData(@RequestHeader("Accept") String acceptHeader) {
    // Validate Accept header
    if (!acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) &&
        !acceptHeader.equalsIgnoreCase("text/csv")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Accept header must be 'application/json' or 'text/csv'");
    }

    return ResponseEntity.ok("Exporting data in the format: " + acceptHeader);
  }

}
