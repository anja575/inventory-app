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
public class StoreDTO {

    private Long id;
    @NotBlank(message = "Store name is mandatory")
    private String name;
    @NotBlank(message = "Store location is mandatory")
    private String location;

}
