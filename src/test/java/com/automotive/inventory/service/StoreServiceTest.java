package com.automotive.inventory.service;

import com.automotive.inventory.domain.Store;
import com.automotive.inventory.dto.StoreDTO;
import com.automotive.inventory.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void createStore() {
        StoreDTO storeDTO = new StoreDTO(null, "Store1", "Location1");
        Store store = new Store(1L, "Store1", "Location1");

        when(storeRepository.save(any(Store.class))).thenReturn(store);

        StoreDTO result = storeService.createStore(storeDTO);

        assertNotNull(result);
        assertEquals(store.getId(), result.getId());
        assertEquals(store.getName(), result.getName());
        assertEquals(store.getLocation(), result.getLocation());
    }

    @Test
    void getAllStores() {
        Store store1 = new Store(5L, "Store1", "Location1");
        Store store2 = new Store(7L, "Store2", "Location2");

        when(storeRepository.findAll()).thenReturn(Arrays.asList(store1, store2));

        List<StoreDTO> result = storeService.getAllStores();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(store1.getName(), result.get(0).getName());
        assertEquals(store2.getName(), result.get(1).getName());
    }
}