package com.daniel.productcatalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class StoreInventory {

  @EmbeddedId
  @JsonIgnore
  private StoreInventoryId storeInventoryId;

  @ManyToOne
  @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "storeId", referencedColumnName = "storeId", insertable = false, updatable = false)
  private Store store;

  private int stockCount;
}