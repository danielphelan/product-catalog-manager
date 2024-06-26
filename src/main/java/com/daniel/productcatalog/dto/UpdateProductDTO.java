package com.daniel.productcatalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class UpdateProductDTO {

  private String title;
  private Double price;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double discountPercentage;

}
