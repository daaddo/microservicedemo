package it.davidecascella.orderservice.client.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryClient inventoryClient;

    public boolean isStockAvailable(String skuCode, Integer quantity) {
        try {
            return inventoryClient.isStockAvailable(skuCode, quantity);
        } catch (Exception e) {
            // Qui puoi gestire il fatto che il servizio Inventory sia down
            // Ad esempio loggando l'errore o restituendo un valore di default
            System.err.println("Errore durante la chiamata al servizio Inventory: " + e.getMessage());
            throw new RuntimeException("Servizio Inventory non disponibile", e);
        }
    }
}
