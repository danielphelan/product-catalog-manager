package com.daniel.productcatalog.service;

import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.repository.ProductRepository;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import java.util.Objects;
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
    Product existingProduct = productRepository.findById(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + productId));

    updateProductFields(existingProduct, updatedProduct);

    productRepository.save(existingProduct);
    return existingProduct;
  }

  private void updateProductFields(Product existingProduct, Product updatedProduct) {
    if (!Objects.equals(existingProduct.getTitle(), updatedProduct.getTitle())) {
      existingProduct.setTitle(updatedProduct.getTitle());
    }
    if (!Objects.equals(existingProduct.getPrice(), updatedProduct.getPrice()) && updatedProduct.getPrice() >= 0) {
      existingProduct.setPrice(updatedProduct.getPrice());
    }
    if (!Objects.equals(existingProduct.getDiscountPercentage(), updatedProduct.getDiscountPercentage())
        && updatedProduct.getDiscountPercentage() >= 0 && updatedProduct.getDiscountPercentage() <= 100) {
      existingProduct.setDiscountPercentage(updatedProduct.getDiscountPercentage());
    }
  }

  public void deleteProduct(Integer productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + productId));

    storeInventoryRepository.deleteByProductId(productId);
    productRepository.delete(product);
  }
}