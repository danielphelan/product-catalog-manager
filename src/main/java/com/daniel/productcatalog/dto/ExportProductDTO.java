package com.daniel.productcatalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class ExportProductDTO {

  private Integer product_id;
  private String title;
  private Double price;
  private String availability;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double sale_price;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double discount_value;

}
