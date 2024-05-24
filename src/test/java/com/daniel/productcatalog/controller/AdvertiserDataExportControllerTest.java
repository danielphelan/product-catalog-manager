package com.daniel.productcatalog.controller;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.daniel.productcatalog.dto.FormatType;
import com.daniel.productcatalog.service.InventoryService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

public class AdvertiserDataExportControllerTest {

    private static HttpServletResponse mockResponse;
    private static AdvertiserDataExportController controller;
    private static InventoryService inventoryService;

    @BeforeAll
    public static void init() {
        inventoryService = mock(InventoryService.class);
        mockResponse = mock(HttpServletResponse.class);
        controller = new AdvertiserDataExportController(inventoryService);
    }

    @Test
    void verify_json_format_handling() throws IOException {
        assertDoesNotThrow(() -> controller.exportAdvertisersData(FormatType.JSON, mockResponse));
        verify(inventoryService).generateAndExportInventoryForAdvertisers(FormatType.JSON, mockResponse);
    }

    // Verify CSV format is correctly handled when specified
    @Test
    void test_csv_format_handling() throws IOException {
        controller.exportAdvertisersData(FormatType.CSV, mockResponse);
    
        // Verify that the method was called with CSV format
        verify(inventoryService).generateAndExportInventoryForAdvertisers(FormatType.CSV, mockResponse);
    }

    @Test
    void test_invalid_format_type_response() {
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            controller.exportAdvertisersData(null, mockResponse);
        });

        String expectedMessage = "Format parameter must be 'JSON' or 'CSV'";
        String actualMessage = exception.getMessage();

      assert actualMessage != null;
      assertTrue(actualMessage.contains(expectedMessage));
    }

}