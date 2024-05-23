package com.daniel.productcatalog.service;

import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.entity.Store;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.entity.StoreInventoryId;
import com.daniel.productcatalog.repository.ProductRepository;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import com.daniel.productcatalog.repository.StoreRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class DataIngestionService {

  private final ProductRepository productRepository;
  private final StoreRepository storeRepository;
  private final StoreInventoryRepository inventoryRepository;

  @Autowired
  public DataIngestionService(ProductRepository productRepository, StoreRepository storeRepository, StoreInventoryRepository inventoryRepository) {
    this.productRepository = productRepository;
    this.storeRepository = storeRepository;
    this.inventoryRepository = inventoryRepository;
  }

  @PostConstruct
  public void loadDataOnStartUp() throws IOException {
    loadCsvData(new ClassPathResource("product-catalog.csv").getInputStream());
  }
  
  public void loadCsvData(InputStream inputStream) {
    try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      List<String[]> rows = reader.readAll();
      List<Product> productList = new ArrayList<>();
      List<Store> storeList = new ArrayList<>();
      List<StoreInventory> inventoryList = new ArrayList<>();

      parseCsvData(rows, productList, storeList, inventoryList);

      productRepository.saveAll(productList);
      storeRepository.saveAll(storeList);
      inventoryRepository.saveAll(inventoryList);
    } catch (IOException | CsvException e) {
      e.printStackTrace();
    }
  }

  private void parseCsvData(List<String[]> rows, List<Product> productList, List<Store> storeList, List<StoreInventory> inventoryList) {
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
        System.err.println("Error parsing numeric value: " + e.getMessage());
        // Handle the parsing error gracefully, e.g., log the error or skip the row
      }
    }
  }
}


