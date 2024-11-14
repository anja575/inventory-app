package com.automotive.inventory.service;

import com.automotive.inventory.domain.Product;
import com.automotive.inventory.dto.ProductDTO;
import com.automotive.inventory.exception.ResourceAlreadyExistsException;
import com.automotive.inventory.mapper.ProductMapper;
import com.automotive.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product newProduct = ProductMapper.mapToProduct(productDTO);
        Optional<Product> product = productRepository.findById(newProduct.getName());
        if(product.isPresent()){
            throw new ResourceAlreadyExistsException("Product with name " + newProduct.getName() + " already exists");
        }
        Product savedProduct = productRepository.save(newProduct);
        return ProductMapper.mapToProductDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductMapper.mapToProductDTO(product)).collect(Collectors.toList());
    }

}
