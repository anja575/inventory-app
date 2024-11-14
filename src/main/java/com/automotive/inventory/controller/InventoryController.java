package com.automotive.inventory.controller;

import com.automotive.inventory.dto.InventoryDTO;
import com.automotive.inventory.dto.ProductInventoryDTO;
import com.automotive.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDTO> updateInventory(@Valid @RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO updatedInventory = inventoryService.updateInventory(inventoryDTO);
        return new ResponseEntity<>(updatedInventory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories(){
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/total")
    public ResponseEntity<List<ProductInventoryDTO>> getTotalInventory(){
        List<ProductInventoryDTO> totalInventory = inventoryService.getTotalQuantityPerProduct();
        return ResponseEntity.ok(totalInventory);
    }

}
