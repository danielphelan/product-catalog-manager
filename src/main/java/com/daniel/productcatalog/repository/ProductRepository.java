package com.daniel.productcatalog.repository;

import com.daniel.productcatalog.entity.ProductCsvRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductCsvRow, Long> {
}
