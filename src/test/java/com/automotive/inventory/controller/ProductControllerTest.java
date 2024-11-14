package com.automotive.inventory.controller;

import com.automotive.inventory.dto.ProductDTO;
import com.automotive.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void createProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO("Product1", "Accessory", "Cotton");

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\", \"type\":\"Accessory\", \"material\":\"Cotton\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Product1")))
                .andExpect(jsonPath("$.type", is("Accessory")))
                .andExpect(jsonPath("$.material", is("Cotton")));
    }

    @Test
    void createProduct_BlankName() throws Exception {
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\", \"type\":\"Accessory\", \"material\":\"Cotton\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Product name is mandatory")));
    }

    @Test
    void createProduct_BlankType() throws Exception {
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\", \"type\":\"\", \"material\":\"Cotton\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type", is("Product type is mandatory")));
    }

    @Test
    void createProduct_BlankMaterial() throws Exception {
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\", \"type\":\"Accessory\", \"material\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.material", is("Product material is mandatory")));
    }


    @Test
    void getAllProducts() throws Exception {
        ProductDTO product1 = new ProductDTO("Product1", "Accessory", "Cotton");
        ProductDTO product2 = new ProductDTO("Product2", "Clothes", "Wool");

        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));

        mockMvc.perform(get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Product1")))
                .andExpect(jsonPath("$[0].type", is("Accessory")))
                .andExpect(jsonPath("$[0].material", is("Cotton")))
                .andExpect(jsonPath("$[1].name", is("Product2")))
                .andExpect(jsonPath("$[1].type", is("Clothes")))
                .andExpect(jsonPath("$[1].material", is("Wool")));
    }
}