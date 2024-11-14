package com.automotive.inventory.service;

import com.automotive.inventory.domain.Store;
import com.automotive.inventory.dto.StoreDTO;
import com.automotive.inventory.mapper.StoreMapper;
import com.automotive.inventory.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public StoreDTO createStore(StoreDTO storeDTO) {
        Store store = StoreMapper.mapToStore(storeDTO);
        Store savedStore= storeRepository.save(store);
        return StoreMapper.mapToStoreDTO(savedStore);
    }

    public List<StoreDTO> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(store -> StoreMapper.mapToStoreDTO(store)).collect(Collectors.toList());
    }

}
