package com.daniel.productcatalog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductExportDTO extends ProductStockDTO{


  public ProductExportDTO(Integer productId, String title, Integer totalStockCount) {
    super(productId, title, totalStockCount);
  }
}

