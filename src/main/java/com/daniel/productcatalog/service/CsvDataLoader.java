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
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class CsvDataLoader {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private StoreRepository storeRepository;

  @Autowired
  private StoreInventoryRepository inventoryRepository;

  @PostConstruct
  public void loadCsvData() {
    try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("product-catalog.csv").getInputStream()))) {
      List<String[]> rows = reader.readAll();
      Map<Integer, Product> products = new HashMap<>();
      Map<Integer, Store> stores = new HashMap<>();

      for (String[] row : rows.subList(1, rows.size())) { // Skip header
        Integer productId = Integer.valueOf(row[0]);
        String title = row[1];
        double price = Double.parseDouble(row[2]);
        Integer storeId = Integer.valueOf(row[3]);
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
        productRepository.save(product);

        Store store = stores.computeIfAbsent(storeId, id -> {
          Store s = new Store();
          s.setStoreId(id);
          s.setStoreCity(storeCity);
          s.setStoreRegion(storeRegion);
          return s;
        });
        storeRepository.save(store);

        StoreInventoryId inventoryId = new StoreInventoryId();
        inventoryId.setProductId(product.getProductId());
        inventoryId.setStoreId(store.getStoreId());

        StoreInventory inventory = new StoreInventory();
        inventory.setStoreInventoryId(inventoryId); // Set the composite
        inventory.setProduct(product);
        inventory.setStore(store);
        inventory.setStockCount(stockCount);

        inventoryRepository.save(inventory);
      }

    } catch (IOException | CsvException e) {
      e.printStackTrace();
    }
  }
}


