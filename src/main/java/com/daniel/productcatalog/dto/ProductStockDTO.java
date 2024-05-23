package com.daniel.productcatalog.dto;

import lombok.Data;

@Data
public class ProductStockDTO extends ProductDTO {

  private Integer totalStockCount;

  public ProductStockDTO(Integer productId, String title, Integer totalStockCount) {
    super(productId, title);
    this.totalStockCount = totalStockCount;
  }

}

