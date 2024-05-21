package com.daniel.productcatalog.repository;

import com.daniel.productcatalog.dto.ProductStockDTO;
import com.daniel.productcatalog.entity.StoreInventory;
import com.daniel.productcatalog.entity.StoreInventoryId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreInventoryRepository extends JpaRepository<StoreInventory, StoreInventoryId> {
  @Query("SELECT new com.daniel.productcatalog.dto.ProductStockDTO(si.product.productId, si.product.title, CAST(SUM(si.stockCount) AS integer)) "
      + "FROM StoreInventory si "
      + "GROUP BY si.product.productId, si.product.title")
  List<ProductStockDTO> findProductsWithTotalStock();

  @Query("SELECT si FROM StoreInventory si WHERE si.storeInventoryId.productId = :productId AND si.storeInventoryId.storeId = :storeId")
  Optional<StoreInventory> findByProductIdAndStoreId(@Param("storeId") Integer storeId, @Param("productId") Integer productId);

  @Modifying
  @Query("DELETE FROM StoreInventory si WHERE si.storeInventoryId.productId = :productId")
  void deleteByProductId(Integer productId);
}

