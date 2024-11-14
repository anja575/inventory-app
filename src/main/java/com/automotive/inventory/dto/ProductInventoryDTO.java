package com.automotive.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryDTO {

    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("total_quantity")
    private Long totalQuantity;

}
