package com.automotive.inventory.repository;

import com.automotive.inventory.domain.Inventory;
import com.automotive.inventory.domain.Product;
import com.automotive.inventory.domain.Store;
import com.automotive.inventory.dto.ProductInventoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductAndStore(Product product, Store store);

    @Query ("SELECT new com.automotive.inventory.dto.ProductInventoryDTO(p.name, SUM(i.quantity)) "+
            "FROM Inventory i JOIN i.product p "+
            "GROUP BY p.name")
    List<ProductInventoryDTO> findTotalQuantityPerProduct();
}
