package com.daniel.productcatalog.controller;

import com.daniel.productcatalog.dto.FormatType;
import com.daniel.productcatalog.service.InventoryService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/export")
public class AdvertiserDataExportController {

  private final InventoryService inventoryService;

  public AdvertiserDataExportController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @GetMapping("/advertisers")
  public void exportAdvertisersData(@RequestParam(name = "format", defaultValue = "json") FormatType format, HttpServletResponse response) throws IOException {

    if (format != FormatType.JSON && format != FormatType.CSV) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format parameter must be 'json' or 'csv'");
    }
    inventoryService.generateAndExportInventoryForAdvertisers(format, response);
  }
}
