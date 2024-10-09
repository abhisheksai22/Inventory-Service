package com.infy.inventory.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
@Builder
public class Inventory {
    @Id
    private Long inventoryId;
    private String name;
    private String email;

}
