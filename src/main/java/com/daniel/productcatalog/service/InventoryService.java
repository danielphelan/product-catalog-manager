package com.daniel.productcatalog.service;

import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.StoreInventory;
import java.util.List;

public interface InventoryService {

  List<StoreInventory> findAllInventory();
  StoreInventory updateInventory(Integer storeId, Integer productId, int stockCount);
  List<ProductStockDTO> findAllProductsWithTotalStock();

}
