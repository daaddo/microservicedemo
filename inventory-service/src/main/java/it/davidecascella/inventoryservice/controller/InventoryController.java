package it.davidecascella.inventoryservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")

public class InventoryController {

    @PreAuthorize("permitAll()")
    @GetMapping("/check")
    public ResponseEntity<Boolean> isStockAvailable(@RequestParam String skuCode, @RequestParam Integer qty) {
        log.info("skuCode={}, quantity={}", skuCode, qty);
        return ResponseEntity.ok().body(true);
    }
}
