package com.automotive.inventory.service;

import com.automotive.inventory.domain.Product;
import com.automotive.inventory.dto.ProductDTO;
import com.automotive.inventory.exception.ResourceAlreadyExistsException;
import com.automotive.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_ProductDoesNotExist() {
        ProductDTO productDTO = new ProductDTO("Product1", "Clothes", "Cotton");
        Product product = new Product("Product1", "Clothes", "Cotton");

        when(productRepository.findById(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO createdProductDTO = productService.createProduct(productDTO);

        assertNotNull(createdProductDTO);
        assertEquals("Product1", createdProductDTO.getName());
        assertEquals("Clothes", createdProductDTO.getType());
        assertEquals("Cotton", createdProductDTO.getMaterial());
    }

    @Test
    void createProduct_ProductExists() {
        ProductDTO productDTO = new ProductDTO("Product1", "Clothes", "Cotton");
        Product product = new Product("Product1", "Clothes", "Cotton");

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        assertThrows(ResourceAlreadyExistsException.class, () -> productService.createProduct(productDTO));
    }

    @Test
    void getAllProducts() {
        Product product1 = new Product("Product1", "Clothes", "Cotton");
        Product product2 = new Product("Product2", "Accessory", "Silk");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1.getName(), result.get(0).getName());
        assertEquals(product2.getName(), result.get(1).getName());
    }
}