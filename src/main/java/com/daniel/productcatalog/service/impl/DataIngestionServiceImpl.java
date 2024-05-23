package com.daniel.productcatalog.service.impl;

import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.entity.Store;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.entity.StoreInventoryId;
import com.daniel.productcatalog.repository.ProductRepository;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import com.daniel.productcatalog.repository.StoreRepository;
import com.daniel.productcatalog.service.DataIngestionService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class DataIngestionServiceImpl implements DataIngestionService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final ProductRepository productRepository;
  private final StoreRepository storeRepository;
  private final StoreInventoryRepository inventoryRepository;

  @Autowired
  public DataIngestionServiceImpl(ProductRepository productRepository, StoreRepository storeRepository, StoreInventoryRepository inventoryRepository) {
    this.productRepository = productRepository;
    this.storeRepository = storeRepository;
    this.inventoryRepository = inventoryRepository;
  }

  @PostConstruct
  public void loadDataOnStartUp() {
    logger.info("Starting data loading from CSV");
    try {
      processCsvData(new ClassPathResource("product-catalog.csv").getInputStream());
      logger.info("Data loading completed successfully");
    } catch (IOException e) {
      logger.error("Failed to load data from CSV", e);
    }
  }

  @Override
  public void processCsvData(InputStream inputStream) {
    logger.info("Processing CSV data");
    try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      List<String[]> rows = reader.readAll();
      List<Product> productList = new ArrayList<>();
      List<Store> storeList = new ArrayList<>();
      List<StoreInventory> inventoryList = new ArrayList<>();

      parseCsvData(rows, productList, storeList, inventoryList);

      productRepository.saveAll(productList);
      storeRepository.saveAll(storeList);
      inventoryRepository.saveAll(inventoryList);
      logger.info("CSV data processed and saved successfully");

    } catch (IOException | CsvException e) {
      logger.error("Error processing CSV data", e);
    }
  }

  private void parseCsvData(List<String[]> rows, List<Product> productList, List<Store> storeList, List<StoreInventory> inventoryList) {
    logger.info("Parsing CSV data");
    Map<Integer, Product> products = new HashMap<>();
    Map<Integer, Store> stores = new HashMap();

    for (String[] row : rows.subList(1, rows.size())) { // Skip header
      try {
        Integer productId = Integer.parseInt(row[0]);
        String title = row[1];
        double price = Double.parseDouble(row[2]);
        Integer storeId = Integer.parseInt(row[3]);
        String storeCity = row[4];
        String storeRegion = row[5];
        int stockCount = Integer.parseInt(row[6]);

        Product product = products.computeIfAbsent(productId, id -> {
          Product p = new Product();
          p.setProductId(id);
          p.setTitle(title);
          p.setPrice(price);
          return p;
        });
        productList.add(product);

        Store store = stores.computeIfAbsent(storeId, id -> {
          Store s = new Store();
          s.setStoreId(id);
          s.setStoreCity(storeCity);
          s.setStoreRegion(storeRegion);
          return s;
        });
        storeList.add(store);

        StoreInventoryId inventoryId = new StoreInventoryId();
        inventoryId.setProductId(product.getProductId());
        inventoryId.setStoreId(store.getStoreId());

        StoreInventory inventory = new StoreInventory();
        inventory.setStoreInventoryId(inventoryId); // Set the composite
        inventory.setProduct(product);
        inventory.setStore(store);
        inventory.setStockCount(stockCount);

        inventoryList.add(inventory);
      } catch (NumberFormatException e) {
        logger.error("Error parsing numeric value", e);
      }
    }
  }
}


