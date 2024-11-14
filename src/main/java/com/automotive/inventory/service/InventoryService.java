package com.automotive.inventory.service;

import com.automotive.inventory.domain.Inventory;
import com.automotive.inventory.domain.Product;
import com.automotive.inventory.domain.Store;
import com.automotive.inventory.dto.InventoryDTO;
import com.automotive.inventory.dto.ProductInventoryDTO;
import com.automotive.inventory.exception.ResourceNotFoundException;
import com.automotive.inventory.mapper.InventoryMapper;
import com.automotive.inventory.repository.InventoryRepository;
import com.automotive.inventory.repository.ProductRepository;
import com.automotive.inventory.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;

    public InventoryDTO updateInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = InventoryMapper.mapToInventory(inventoryDTO);
        Optional<Product> optionalProduct = productRepository.findById(inventory.getProduct().getName());
        if(!optionalProduct.isPresent()) {
            throw new ResourceNotFoundException("Product with " + inventory.getProduct().getName() + " name does not exist");
        }

        Optional<Store> optionalStore = storeRepository.findById(inventory.getStore().getId());
        if(!optionalStore.isPresent()) {
            throw new ResourceNotFoundException("Store with id " + inventory.getStore().getId() + " does not exist");
        }

        Product product = optionalProduct.get();
        Store store = optionalStore.get();

        Optional<Inventory> optionalInventory = inventoryRepository.findByProductAndStore(product, store);

        if(optionalInventory.isPresent()){
            Inventory existingInventory = optionalInventory.get();
            existingInventory.setQuantity(inventory.getQuantity());
            Inventory updatedInventory = inventoryRepository.save(existingInventory);
            return InventoryMapper.mapToInventoryDTO(updatedInventory);
        } else {
            inventory.setProduct(product);
            inventory.setStore(store);
            Inventory newInventory = inventoryRepository.save(inventory);
            return InventoryMapper.mapToInventoryDTO(newInventory);
        }

    }

    public List<InventoryDTO> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream().map(inventory -> InventoryMapper.mapToInventoryDTO(inventory)).collect(Collectors.toList());
    }

    public List<ProductInventoryDTO> getTotalQuantityPerProduct() {
        return inventoryRepository.findTotalQuantityPerProduct();
    }
}
