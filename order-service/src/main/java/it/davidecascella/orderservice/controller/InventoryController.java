package it.davidecascella.orderservice.controller;


import it.davidecascella.orderservice.client.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@EnableMethodSecurity
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> checkInventory() {
        return ResponseEntity.ok("Inventory Service is up and running!");
    }

    @GetMapping("/check/{sku}/{qty}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Boolean> getInventory(@PathVariable String sku, @PathVariable Integer qty) {
        boolean stockAvailable = inventoryService.isStockAvailable(sku, qty);
        return ResponseEntity.ok().body(stockAvailable);
    }
}
