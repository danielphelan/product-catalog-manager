package com.daniel.productcatalog.service;

import com.daniel.productcatalog.entity.Store;
import com.daniel.productcatalog.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final StoreRepository storeRepository;

  public StoreService(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  public List<Store> findStoresByRegion(final String region) {
    logger.info("Searching for stores in region: '{}'", region);
    if (region == null || region.isEmpty()) {
      logger.info("Region is null or empty, fetching all stores");
      List<Store> stores = storeRepository.findAll();
      logger.info("Number of stores found: {}", stores.size());
      return stores;
    }
    List<Store> stores = storeRepository.findByStoreRegion(region);
    logger.info("Number of stores found in region '{}': {}", region, stores.size());
    return stores;
  }
}