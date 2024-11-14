package com.automotive.inventory.service;

import com.automotive.inventory.domain.Inventory;
import com.automotive.inventory.domain.Product;
import com.automotive.inventory.domain.Store;
import com.automotive.inventory.dto.InventoryDTO;
import com.automotive.inventory.dto.ProductInventoryDTO;
import com.automotive.inventory.exception.ResourceNotFoundException;
import com.automotive.inventory.repository.InventoryRepository;
import com.automotive.inventory.repository.ProductRepository;
import com.automotive.inventory.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private InventoryDTO inventoryDTO;
    private Inventory inventory;
    private Product product;
    private Store store;

    @BeforeEach
    public void setUp() {
        inventoryDTO = new InventoryDTO("Product1", 1L, 12);
        inventory = new Inventory("Product1", 1L, 10);
        product= new Product();
        product.setName("Product1");
        store = new Store();
        store.setId(1L);
    }

    @Test
    public void updateInventory_ProductDoesNotExist() {
        when(productRepository.findById("Product1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            inventoryService.updateInventory(inventoryDTO);
        });
    }

    @Test
    public void updateInventory_StoreDoesNotExist() {
        when(productRepository.findById("Product1")).thenReturn(Optional.of(product));
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            inventoryService.updateInventory(inventoryDTO);
        });
    }

    @Test
    public void updateInventory_InventoryExists() {
        when(productRepository.findById("Product1")).thenReturn(Optional.of(product));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(inventoryRepository.findByProductAndStore(product, store)).thenReturn(Optional.of(inventory));

        inventory.setQuantity(inventoryDTO.getQuantity());
        when(inventoryRepository.save(inventory)).thenReturn(inventory);

        InventoryDTO result = inventoryService.updateInventory(inventoryDTO);

        assertNotNull(result);
        assertEquals(inventoryDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void updateInventory_InventoryDoesNotExist() {
        when(productRepository.findById("Product1")).thenReturn(Optional.of(product));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(inventoryRepository.findByProductAndStore(product, store)).thenReturn(Optional.empty());

        Inventory newInventory = new Inventory();
        newInventory.setProduct(product);
        newInventory.setStore(store);
        newInventory.setQuantity(inventoryDTO.getQuantity());
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(newInventory);

        InventoryDTO result = inventoryService.updateInventory(inventoryDTO);

        assertNotNull(result);
        assertEquals(inventoryDTO.getQuantity(), result.getQuantity());
        assertEquals(inventoryDTO.getProductName(), result.getProductName());
        assertEquals(inventoryDTO.getStoreId(), result.getStoreId());
    }

    @Test
    public void getAllInventories(){
        Inventory inventory1 = new Inventory("Product2", 2L, 5);
        Inventory inventory2 = new Inventory("Product3", 3L, 7);

        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory1, inventory2));

        List<InventoryDTO> result = inventoryService.getAllInventories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(inventory1.getQuantity(), result.get(0).getQuantity());
        assertEquals(inventory2.getQuantity(), result.get(1).getQuantity());
    }

    @Test
    public void getTotalQuantityPerProduct(){
        ProductInventoryDTO product1 = new ProductInventoryDTO("Product4", 17L);
        ProductInventoryDTO product2 = new ProductInventoryDTO("Product5", 11L);

        when(inventoryRepository.findTotalQuantityPerProduct()).thenReturn(Arrays.asList(product1, product2));

        List<ProductInventoryDTO> result = inventoryService.getTotalQuantityPerProduct();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product4", result.get(0).getProductName());
        assertEquals(17L, result.get(0).getTotalQuantity());
        assertEquals("Product5", result.get(1).getProductName());
        assertEquals(11L, result.get(1).getTotalQuantity());
    }
}