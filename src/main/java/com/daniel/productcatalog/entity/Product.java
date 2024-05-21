package com.daniel.productcatalog.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {

  @Id
  private Integer productId;
  private String title;
  private Double price;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double discountPercentage;
}
