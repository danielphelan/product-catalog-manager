package com.daniel.productcatalog.service;

import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.repository.ProductRepository;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final StoreInventoryRepository storeInventoryRepository;


  @Autowired
  public ProductService(ProductRepository productRepository, StoreInventoryRepository storeInventoryRepository) {
    this.productRepository = productRepository;
    this.storeInventoryRepository = storeInventoryRepository;
  }

  public Optional<Product> findProductById(Integer productId) {
    return productRepository.findById(productId);
  }

  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(Integer productId, Product updatedProduct) {
    productRepository.findById(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + productId));

    productRepository.save(updatedProduct);
    return updatedProduct;
  }

  public void deleteProduct(Integer productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + productId));

    storeInventoryRepository.deleteByProductId(productId);
    productRepository.delete(product);
  }
}