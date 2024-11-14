package com.automotive.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String location;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Inventory> inventories;

    public Store(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Store(long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
