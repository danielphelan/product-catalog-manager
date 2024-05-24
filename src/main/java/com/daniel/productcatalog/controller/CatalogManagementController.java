package com.daniel.productcatalog.controller;

import com.daniel.productcatalog.dto.UpdateProductDTO;
import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.entity.Store;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.service.impl.InventoryServiceImpl;
import com.daniel.productcatalog.service.impl.ProductServiceImpl;
import com.daniel.productcatalog.service.impl.StoreServiceImpl;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final InventoryServiceImpl inventoryService;
  private final StoreServiceImpl storeService;
  private final ProductServiceImpl productService;

  public CatalogManagementController(InventoryServiceImpl inventoryService, StoreServiceImpl storeService, ProductServiceImpl productService) {
    this.inventoryService = inventoryService;
    this.storeService = storeService;
    this.productService = productService;
  }

  @GetMapping("/products")
  public ResponseEntity<List<StoreInventory>> getAllProducts() {
    logger.info("Request received to fetch all products from inventory");
    List<StoreInventory> inventoryList = inventoryService.findAllInventory();
    logger.info("Fetched {} products from inventory", inventoryList.size());
    return ResponseEntity.ok(inventoryList);
  }

  @GetMapping("/products/total-stock")
  public ResponseEntity<List<ProductStockDTO>> getAllProductsWithTotalStock() {
    logger.info("Request received to fetch all products with total stock");
    List<ProductStockDTO> productsWithStock = inventoryService.findAllProductsWithTotalStock();
    logger.info("Fetched total stock for {} products", productsWithStock.size());
    return ResponseEntity.ok(productsWithStock);
  }

  @PutMapping("/products/{productId}")
  @Transactional
  public ResponseEntity<Product> updateProductDetails(
      @PathVariable Integer productId,
      @RequestBody UpdateProductDTO updatedProduct) {
    logger.info("Request received to update product details for productId: {}", productId);
    Product product = productService.updateProduct(productId, updatedProduct);
    logger.info("Product details updated for productId: {}", productId);
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/products/{productId}")
  @Transactional
  public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
    logger.info("Request received to delete product with productId: {}", productId);
    productService.deleteProduct(productId);
    logger.info("Product deleted with productId: {}", productId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/inventory/{storeId}/{productId}/stock")
  @Transactional
  public ResponseEntity<StoreInventory> updateStockCount(
      @PathVariable Integer storeId,
      @PathVariable Integer productId,
      @RequestParam int stockCount) {
    logger.info("Request received to update stock count for storeId: {}, productId: {}, new stock count: {}", storeId, productId, stockCount);
    StoreInventory inventory = inventoryService.updateInventory(storeId, productId, stockCount);
    logger.info("Stock count updated for storeId: {}, productId: {}", storeId, productId);
    return ResponseEntity.ok(inventory);
  }

  @GetMapping("/stores")
  public ResponseEntity<List<Store>> getAllStores(@RequestParam(required = false) String region) {
    if (region != null) {
      logger.info("Request received to fetch all stores in region: {}", region);
    } else {
      logger.info("Request received to fetch all stores");
    }
    List<Store> stores = storeService.findStoresByRegion(region);
    logger.info("Fetched {} stores", stores.size());
    return ResponseEntity.ok(stores);
  }

}