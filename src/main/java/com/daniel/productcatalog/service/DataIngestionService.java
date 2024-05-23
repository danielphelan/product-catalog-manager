package com.daniel.productcatalog.service;

import java.io.InputStream;

public interface DataIngestionService {
  void processCsvData(InputStream inputStream);
}
