package com.daniel.productcatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog")
public class CatalogManagementController {

  @GetMapping("/products")
  public ResponseEntity<String> getAllProducts() {
    return ResponseEntity.ok("Retrieved all products from catalog");
  }
}
