package com.automotive.inventory.mapper;

import com.automotive.inventory.domain.Product;
import com.automotive.inventory.dto.ProductDTO;

public class ProductMapper {

    public static ProductDTO mapToProductDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getType(),
                product.getMaterial()
        );
    }

    public static Product mapToProduct(ProductDTO productDTO) {
        return new Product(
                productDTO.getName(),
                productDTO.getType(),
                productDTO.getMaterial()
        );
    }
}
