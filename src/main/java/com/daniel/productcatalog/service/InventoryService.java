package com.daniel.productcatalog.service;

import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final StoreInventoryRepository inventoryRepository;

  @Autowired
  public InventoryService(StoreInventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  public List<StoreInventory> findAllInventory() {
    logger.info("Fetching all inventory");
    return inventoryRepository.findAll();
  }

  public StoreInventory updateInventory(Integer storeId, Integer productId, int stockCount) {
    logger.info("Updating inventory for storeId: {} and productId: {}", storeId, productId);
    StoreInventory inventory = inventoryRepository.findByProductIdAndStoreId(storeId, productId)
        .orElseThrow(() -> {
          logger.error("Inventory not found with storeId: {} and productId: {}", storeId, productId);
          return new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with store " + storeId + " and product id " + productId);
        });

    inventory.setStockCount(stockCount);
    StoreInventory updatedInventory = inventoryRepository.save(inventory);
    logger.info("Updated inventory with storeId: {} and productId: {}. New stock count: {}", storeId, productId, stockCount);
    return updatedInventory;
  }

  public List<ProductStockDTO> findAllProductsWithTotalStock() {
    logger.info("Fetching all products with total stock");
    return inventoryRepository.findProductsWithTotalStock();
  }
}