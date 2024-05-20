package com.daniel.productcatalog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProductCsvRow {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String productId;
  private String title;
  private double price;
  private String storeId;
  private String storeCity;
  private String storeRegion;
  private int stockCount;

}
