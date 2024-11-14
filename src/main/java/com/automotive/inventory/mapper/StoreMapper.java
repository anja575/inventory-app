package com.automotive.inventory.mapper;

import com.automotive.inventory.domain.Store;
import com.automotive.inventory.dto.StoreDTO;

public class StoreMapper {

    public static StoreDTO mapToStoreDTO(Store store) {
        return new StoreDTO(
                store.getId(),
                store.getName(),
                store.getLocation()
        );
    }

    public static Store mapToStore(StoreDTO storeDTO) {
        return new Store(
                storeDTO.getName(),
                storeDTO.getLocation()
        );
    }
}
