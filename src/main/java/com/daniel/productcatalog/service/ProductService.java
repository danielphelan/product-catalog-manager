package com.daniel.productcatalog.service;

import com.daniel.productcatalog.dto.UpdateProductDTO;
import com.daniel.productcatalog.entity.Product;

public interface ProductService {

  Product updateProduct(Integer productId, UpdateProductDTO updatedProduct);
  void deleteProduct(Integer productId);

}
