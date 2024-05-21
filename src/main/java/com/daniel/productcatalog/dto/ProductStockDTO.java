package com.daniel.productcatalog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockDTO {

  private Integer productId;
  private String title;
  private Integer totalStockCount;

  public ProductStockDTO(Integer productId, String title, Integer totalStockCount) {
    this.productId = productId;
    this.title = title;
    this.totalStockCount = totalStockCount;
  }

}

