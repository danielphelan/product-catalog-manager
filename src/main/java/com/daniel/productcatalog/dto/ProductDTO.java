package com.daniel.productcatalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ProductDTO {

  private Integer productId;
  private String title;
  private Double price;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double discountPercentage;

  public ProductDTO(Integer productId, String title) {
    this.productId = productId;
    this.title = title;
  }
}
