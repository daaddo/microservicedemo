package it.davidecascella.inventoryservice.repository;

import it.davidecascella.inventoryservice.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    Optional<InventoryItem> findBySkuCode(String skuCode);
}
