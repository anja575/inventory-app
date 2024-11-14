package com.automotive.inventory.controller;

import com.automotive.inventory.dto.StoreDTO;
import com.automotive.inventory.service.StoreService;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Test
    public void createStore() throws Exception {
        StoreDTO createdStoreDTO = new StoreDTO(1L, "Store1", "Location1");

        when(storeService.createStore(any(StoreDTO.class))).thenReturn(createdStoreDTO);

        mockMvc.perform(post("/api/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Store1\",\"location\":\"Location1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Store1"))
                .andExpect(jsonPath("$.location").value("Location1"));
    }

    @Test
    public void createStore_BlankName() throws Exception {
        mockMvc.perform(post("/api/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"location\":\"Location1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Store name is mandatory")));
    }

    @Test
    public void createStore_BlankLocation() throws Exception {
        mockMvc.perform(post("/api/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Store1\",\"location\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.location", is("Store location is mandatory")));
    }

    @Test
    public void getAllStores() throws Exception {
        StoreDTO store1 = new StoreDTO(5L, "Store1", "Location1");
        StoreDTO store2 = new StoreDTO(7L, "Store2", "Location2");

        when(storeService.getAllStores()).thenReturn(List.of(store1, store2));

        mockMvc.perform(get("/api/store")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(5)))
                .andExpect(jsonPath("$[0].name", is("Store1")))
                .andExpect(jsonPath("$[0].location", is("Location1")))
                .andExpect(jsonPath("$[1].id", is(7)))
                .andExpect(jsonPath("$[1].name", is("Store2")))
                .andExpect(jsonPath("$[1].location", is("Location2")));
    }
}