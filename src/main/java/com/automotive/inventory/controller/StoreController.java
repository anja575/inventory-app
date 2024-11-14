package com.automotive.inventory.controller;

import com.automotive.inventory.dto.StoreDTO;
import com.automotive.inventory.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@Valid @RequestBody StoreDTO storeDTO) {
        return new ResponseEntity<>(storeService.createStore(storeDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores(){
        return ResponseEntity.ok(storeService.getAllStores());
    }

}
