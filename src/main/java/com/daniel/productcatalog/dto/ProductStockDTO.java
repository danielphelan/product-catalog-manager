package com.daniel.productcatalog.dto;

public class ProductStockDTO {

  private Integer productId;
  private String title;
  private Integer totalStockCount;

  public ProductStockDTO(Integer productId, String title, Integer totalStockCount) {
    this.productId = productId;
    this.title = title;
    this.totalStockCount = totalStockCount;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getTotalStockCount() {
    return totalStockCount;
  }

  public void setTotalStockCount(Integer totalStockCount) {
    this.totalStockCount = totalStockCount;
  }
}

