package it.davidecascella.orderservice.client.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "${application.config.inventory-url}")
public interface InventoryClient {

    @GetMapping("/api/v1/inventory/check")
    boolean isStockAvailable(@RequestParam("skuCode") String skuCode, @RequestParam("qty") Integer quantity);
}
