package com.automotive.inventory.mapper;

import com.automotive.inventory.domain.Inventory;
import com.automotive.inventory.dto.InventoryDTO;

public class InventoryMapper {

    public static InventoryDTO mapToInventoryDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getProduct().getName(),
                inventory.getStore().getId(),
                inventory.getQuantity()
        );
    }

    public static Inventory mapToInventory(InventoryDTO inventoryDTO) {
        return new Inventory(
                inventoryDTO.getProductName(),
                inventoryDTO.getStoreId(),
                inventoryDTO.getQuantity()
        );
    }
}
