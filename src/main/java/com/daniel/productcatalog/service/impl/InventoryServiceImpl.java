package com.daniel.productcatalog.service.impl;

import com.daniel.productcatalog.controller.AdvertiserDataExportController;
import com.daniel.productcatalog.dto.ExportProductDTO;
import com.daniel.productcatalog.dto.FormatType;
import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import com.daniel.productcatalog.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryServiceImpl implements InventoryService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final StoreInventoryRepository inventoryRepository;
  private static final String[] CSV_HEADER = { "product_id", "title", "price", "availability", "sale_price", "discount_value" };
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public InventoryServiceImpl(StoreInventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  @Override
  public List<StoreInventory> findAllInventory() {
    logger.info("Fetching all inventory");
    return inventoryRepository.findAll();
  }

  @Override
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

  @Override
  public List<ProductStockDTO> findAllProductsWithTotalStock() {
    logger.info("Fetching all products with total stock");
    return inventoryRepository.findProductsWithTotalStock();
  }

  @Override
  public void generateAndExportInventoryForAdvertisers(FormatType format, HttpServletResponse response)
      throws IOException {
    List<StoreInventory> inventoryList = inventoryRepository.findAll();
    List<ExportProductDTO> exportProductDTOs = mapToExportProductDTO(inventoryList);

    if (format == FormatType.JSON) {
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(objectMapper.writeValueAsString(exportProductDTOs));
    } else {
      response.setContentType("text/csv; charset=utf-8");
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"advertisers.csv\"");
      writeCsv(response, exportProductDTOs);
    }

  }

  private static void writeCsv(HttpServletResponse response, List<ExportProductDTO> exportProductDTOs) throws IOException {
    CSVWriter csvWriter = new CSVWriter(response.getWriter());
    csvWriter.writeNext(CSV_HEADER);

    for (ExportProductDTO dto : exportProductDTOs) {
      String[] data = {
          dto.getProduct_id().toString(),
          dto.getTitle(),
          dto.getPrice().toString(),
          dto.getAvailability(),
          dto.getSale_price() != null ? dto.getSale_price().toString() : "",
          dto.getDiscount_value() != null ? dto.getDiscount_value().toString() : ""
      };
      csvWriter.writeNext(data);
    }
    csvWriter.close();
  }

  private static List<ExportProductDTO> mapToExportProductDTO(List<StoreInventory> storeInventories) {
    Map<Product, List<StoreInventory>> groupedByProduct = storeInventories.stream()
        .collect(Collectors.groupingBy(StoreInventory::getProduct));

    return groupedByProduct.entrySet().stream()
        .map(InventoryServiceImpl::convertToDTO)
        .collect(Collectors.toList());
  }

  private static ExportProductDTO convertToDTO(Map.Entry<Product, List<StoreInventory>> entry) {
    Product product = entry.getKey();
    List<StoreInventory> inventories = entry.getValue();

    int totalStockCount = inventories.stream().mapToInt(StoreInventory::getStockCount).sum();

    ExportProductDTO dto = new ExportProductDTO();
    dto.setProduct_id(product.getProductId());
    dto.setTitle(product.getTitle());
    dto.setPrice(product.getPrice());
    dto.setAvailability(totalStockCount > 0 ? "Available" : "Out of Stock");

    Double discountPercentage = product.getDiscountPercentage();
    dto.setDiscount_value(discountPercentage);
    if (discountPercentage != null && discountPercentage > 0) {
      double salePrice = product.getPrice() * (1 - discountPercentage / 100);
      dto.setSale_price(salePrice);
    }

    return dto;
  }

}