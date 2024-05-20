package com.daniel.productcatalog.controller;

import com.daniel.productcatalog.entity.ProductCsvRow;
import com.daniel.productcatalog.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog")
public class CatalogManagementController {

  @Autowired
  private ProductRepository productCsvRowRepository;

  @GetMapping("/products")
  public ResponseEntity<List<ProductCsvRow>> getAllProducts() {
    List<ProductCsvRow> products = productCsvRowRepository.findAll();
    return ResponseEntity.ok(products);
  }
}
