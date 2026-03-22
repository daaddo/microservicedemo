package it.davidecascella.inventoryservice.controller;

import it.davidecascella.inventoryservice.entity.InventoryItem;
import it.davidecascella.inventoryservice.repository.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final InventoryItemRepository inventoryItemRepository;

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        return ResponseEntity.ok(inventoryItemRepository.findAll());
    }
}
