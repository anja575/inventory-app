package com.automotive.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

    @NotBlank(message = "Product name is mandatory")
    @JsonProperty("product_name")
    private String productName;
    @NotNull(message = "Store id is mandatory")
    @JsonProperty("store_id")
    @Positive(message = "Store id must be a positive number")
    private Long storeId;
    @NotNull(message = "Quantity is mandatory")
    @PositiveOrZero(message = "Quantity must be zero or positive number")
    private Integer quantity;

}
