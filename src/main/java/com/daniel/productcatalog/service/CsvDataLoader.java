package com.daniel.productcatalog.service;

import com.daniel.productcatalog.entity.ProductCsvRow;
import com.daniel.productcatalog.repository.ProductRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class CsvDataLoader {

  @Autowired
  private ProductRepository productCsvRowRepository;

  @PostConstruct
  public void loadCsvData() {
    try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("product-catalog.csv").getInputStream()))) {
      List<String[]> rows = reader.readAll();
      List<ProductCsvRow> products = rows.stream().skip(1) // Skip header
          .map(this::mapToProductCsvRow)
          .collect(Collectors.toList());
      productCsvRowRepository.saveAll(products);
    } catch (IOException | CsvException e) {
      e.printStackTrace();
    }
  }

  private ProductCsvRow mapToProductCsvRow(String[] row) {
    ProductCsvRow product = new ProductCsvRow();
    product.setProductId(row[0]);
    product.setTitle(row[1]);
    product.setPrice(Double.parseDouble(row[2]));
    product.setStoreId(row[3]);
    product.setStoreCity(row[4]);
    product.setStoreRegion(row[5]);
    product.setStockCount(Integer.parseInt(row[6]));
    return product;
  }

}
