package com.automotive.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Product name is mandatory")
    private String name;
    @NotBlank(message = "Product type is mandatory")
    private String type;
    @NotBlank(message = "Product material is mandatory")
    private String material;
}
