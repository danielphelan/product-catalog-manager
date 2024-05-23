package com.daniel.productcatalog.service;

import com.daniel.productcatalog.entity.Store;
import java.util.List;

public interface StoreService {

  List<Store> findStoresByRegion(final String region);

}
