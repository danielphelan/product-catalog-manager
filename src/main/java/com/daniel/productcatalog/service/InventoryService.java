package com.daniel.productcatalog.service;

import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryService {

  private final StoreInventoryRepository inventoryRepository;

  @Autowired
  public InventoryService(StoreInventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  public List<StoreInventory> findAllInventory() {
    return inventoryRepository.findAll();
  }

  public StoreInventory updateInventory(Integer storeId, Integer productId, int stockCount) {
    StoreInventory inventory = inventoryRepository.findByProductIdAndStoreId(storeId, productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND," Inventory not found with store " + storeId + " and product id " + productId));

    inventory.setStockCount(stockCount);
    return inventoryRepository.save(inventory);
  }

  public List<ProductStockDTO> findAllProductsWithTotalStock() {
    return inventoryRepository.findProductsWithTotalStock();
  }
}