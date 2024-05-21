package com.daniel.productcatalog.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Store {

  @Id
  private Integer storeId;
  private String storeCity;
  private String storeRegion;
}
