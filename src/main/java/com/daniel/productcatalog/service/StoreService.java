package com.daniel.productcatalog.service;

import com.daniel.productcatalog.entity.Store;
import com.daniel.productcatalog.repository.StoreRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

  private final StoreRepository storeRepository;

  @Autowired
  public StoreService(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  public List<Store> findAllStores() {
    return storeRepository.findAll();
  }

  public List<Store> findStoresByRegion(String region) {
    if (region == null || region.isEmpty()) {
      return storeRepository.findAll();
    }
    return storeRepository.findByStoreRegion(region);
  }
}