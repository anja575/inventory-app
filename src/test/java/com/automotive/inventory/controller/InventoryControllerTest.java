package com.automotive.inventory.controller;

import com.automotive.inventory.dto.InventoryDTO;
import com.automotive.inventory.dto.ProductInventoryDTO;
import com.automotive.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    public void updateInventory() throws Exception {
        InventoryDTO inventoryDTO = new InventoryDTO("Product1", 55L, 15);

        when(inventoryService.updateInventory(any(InventoryDTO.class))).thenReturn(inventoryDTO);

        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product_name\":\"Product1\", \"store_id\":55,  \"quantity\":15}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.product_name", is("Product1")))
                .andExpect(jsonPath("$.store_id", is(55)))
                .andExpect(jsonPath("$.quantity", is(15)));

    }

    @Test
    public void updateInventory_BlankProductName() throws Exception {
        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product_name\":\"\", \"store_id\":1, \"quantity\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.productName", is("Product name is mandatory")));
    }

    @Test
    public void updateInventory_BlankStoreId() throws Exception {
        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product_name\":\"Product1\", \"store_id\":null, \"quantity\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.storeId", is("Store id is mandatory")));
    }

    @Test
    public void updateInventory_NegativeStoreId() throws Exception {
        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product_name\":\"Product1\", \"store_id\":-1, \"quantity\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.storeId", is("Store id must be a positive number")));
    }

    @Test
    public void updateInventory_BlankQuantity() throws Exception {
        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product_name\":\"Product1\", \"store_id\":1, \"quantity\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.quantity", is("Quantity is mandatory")));
    }

    @Test
    public void updateInventory_NegativeQuantity() throws Exception {
        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product_name\":\"Product1\", \"store_id\":1, \"quantity\":-5}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.quantity", is("Quantity must be zero or positive number")));
    }

    @Test
    public void getAllInventories() throws Exception {
        InventoryDTO inventory1 = new InventoryDTO("Product1", 33L, 12);
        InventoryDTO inventory2 = new InventoryDTO("Product2", 44L, 17);

        when(inventoryService.getAllInventories()).thenReturn(Arrays.asList(inventory1, inventory2));

        mockMvc.perform(get("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].product_name", is("Product1")))
                .andExpect(jsonPath("$[0].store_id", is(33)))
                .andExpect(jsonPath("$[0].quantity", is(12)))
                .andExpect(jsonPath("$[1].product_name", is("Product2")))
                .andExpect(jsonPath("$[1].store_id", is(44)))
                .andExpect(jsonPath("$[1].quantity", is(17)));
    }

    @Test
    public void getTotalInventory() throws Exception {
        ProductInventoryDTO product1 = new ProductInventoryDTO("Product1", 77L);
        ProductInventoryDTO product2 = new ProductInventoryDTO("Product2", 88L);

        when(inventoryService.getTotalQuantityPerProduct()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/inventory/total")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].product_name", is("Product1")))
                .andExpect(jsonPath("$[0].total_quantity", is(77)))
                .andExpect(jsonPath("$[1].product_name", is("Product2")))
                .andExpect(jsonPath("$[1].total_quantity", is(88)));
    }
}