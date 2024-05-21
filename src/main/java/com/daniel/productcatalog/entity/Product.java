package com.daniel.productcatalog.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {

  @Id
  private Integer productId;
  private String title;
  private double price;

}
