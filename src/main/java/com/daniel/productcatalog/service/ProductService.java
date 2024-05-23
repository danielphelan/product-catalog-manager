package com.daniel.productcatalog.service;

import com.daniel.productcatalog.dto.ProductDTO;
import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.entity.StoreInventory;
import java.util.List;

public interface ProductService {

  Product updateProduct(Integer productId, ProductDTO updatedProduct);
  void deleteProduct(Integer productId);

}
