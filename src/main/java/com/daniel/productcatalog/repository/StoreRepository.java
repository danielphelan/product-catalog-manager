package com.daniel.productcatalog.repository;

import com.daniel.productcatalog.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
  List<Store> findByStoreRegion(String storeRegion);
}
