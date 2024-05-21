package com.daniel.productcatalog.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import java.util.Objects;
import lombok.Data;

@Embeddable
@Data
public class StoreInventoryId implements Serializable {

  private Integer productId;
  private Integer storeId;

}
