package com.daniel.productcatalog.controller;

import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.entity.Store;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.service.InventoryService;
import com.daniel.productcatalog.service.ProductService;
import com.daniel.productcatalog.service.StoreService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog")
public class CatalogManagementController {

  private final InventoryService inventoryService;
  private final StoreService storeService;
  private final ProductService productService;

  @Autowired
  public CatalogManagementController(InventoryService inventoryService, StoreService storeService, ProductService productService) {
    this.inventoryService = inventoryService;
    this.storeService = storeService;
    this.productService = productService;
  }

  @GetMapping("/products")
  public ResponseEntity<List<StoreInventory>> getAllProducts() {
    List<StoreInventory> inventoryList = inventoryService.findAllInventory();
    return ResponseEntity.ok(inventoryList);
  }

  @GetMapping("/products/total-stock")
  public ResponseEntity<List<ProductStockDTO>> getAllProductsWithTotalStock() {
    List<ProductStockDTO> productsWithStock = inventoryService.findAllProductsWithTotalStock();
    return ResponseEntity.ok(productsWithStock);
  }

  @PutMapping("/products/{productId}")
  @Transactional
  public ResponseEntity<Product> updateProductDetails(
      @PathVariable Integer productId,
      @RequestBody Product updatedProduct) {
    Product product = productService.updateProduct(productId, updatedProduct);
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/products/{productId}")
  @Transactional
  public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/inventory/{storeId}/{productId}/stock")
  @Transactional
  public ResponseEntity<StoreInventory> updateStockCount(
      @PathVariable Integer storeId,
      @PathVariable Integer productId,
      @RequestParam int stockCount) {
    StoreInventory inventory = inventoryService.updateInventory(storeId, productId, stockCount);
    return ResponseEntity.ok(inventory);
  }

  @GetMapping("/stores")
  public ResponseEntity<List<Store>> getAllStores(@RequestParam(required = false) String region) {
    List<Store> stores = storeService.findStoresByRegion(region);
    return ResponseEntity.ok(stores);
  }

}