package com.daniel.productcatalog.service;

import com.daniel.productcatalog.dto.FormatType;
import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.StoreInventory;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface InventoryService {

  List<StoreInventory> findAllInventory();
  StoreInventory updateInventory(Integer storeId, Integer productId, int stockCount);
  List<ProductStockDTO> findAllProductsWithTotalStock();
  void generateAndExportInventoryForAdvertisers(FormatType format, HttpServletResponse response) throws IOException;

}
