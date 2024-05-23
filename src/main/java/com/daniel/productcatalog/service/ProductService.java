package com.daniel.productcatalog.service;

import com.daniel.productcatalog.dto.ProductDTO;
import com.daniel.productcatalog.entity.Product;
import com.daniel.productcatalog.repository.ProductRepository;
import com.daniel.productcatalog.repository.StoreInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final ProductRepository productRepository;
  private final StoreInventoryRepository storeInventoryRepository;

  private static final int MIN_PRICE = 1;
  private static final int MIN_DISCOUNT_PERCENTAGE = 1;
  private static final int MAX_DISCOUNT_PERCENTAGE = 99;

  public ProductService(ProductRepository productRepository, StoreInventoryRepository storeInventoryRepository) {
    this.productRepository = productRepository;
    this.storeInventoryRepository = storeInventoryRepository;
  }

  public Product updateProduct(Integer productId, ProductDTO updatedProduct) {
    logger.info("Updating product with ID: {}", productId);
    Product existingProduct = getProductById(productId);

    updateProductFields(existingProduct, updatedProduct);
    productRepository.save(existingProduct);
    logger.info("Updated product with ID: {}", productId);
    return existingProduct;
  }

  public void deleteProduct(Integer productId) {
    logger.info("Deleting product with ID: {}", productId);
    Product product = getProductById(productId);

    storeInventoryRepository.deleteByProductId(productId);
    productRepository.delete(product);
    logger.info("Deleted product with ID: {}", productId);
  }

  private void updateProductFields(Product existingProduct, ProductDTO updatedProduct) {

    if (updatedProduct.getTitle() != null) {
      logger.info("Updating title to: {}", updatedProduct.getTitle());
      existingProduct.setTitle(updatedProduct.getTitle());
    }
    if (updatedProduct.getPrice() != null && updatedProduct.getPrice() >= MIN_PRICE) {
      logger.info("Updating price to: {}", updatedProduct.getPrice());
      existingProduct.setPrice(updatedProduct.getPrice());
    }
    if (updatedProduct.getDiscountPercentage() != null && updatedProduct.getDiscountPercentage() >= MIN_DISCOUNT_PERCENTAGE
        && updatedProduct.getDiscountPercentage() <= MAX_DISCOUNT_PERCENTAGE) {
      logger.info("Updating discount percentage to: {}", updatedProduct.getDiscountPercentage());
      existingProduct.setDiscountPercentage(updatedProduct.getDiscountPercentage());
    }
  }

  private Product getProductById(Integer productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> {
          logger.error("Product not found with ID: {}", productId);
          return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + productId);
        });
  }
}