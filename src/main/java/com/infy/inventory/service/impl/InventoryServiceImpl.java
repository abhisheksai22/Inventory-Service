package com.infy.inventory.service.impl;

import com.infy.inventory.dto.InventoryRequest;
import com.infy.inventory.dto.InventoryResponse;
import com.infy.inventory.exception.InventoryServiceBusinessException;
import com.infy.inventory.model.Inventory;
import com.infy.inventory.repo.InventoryRepository;
import com.infy.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    @Override
    public InventoryResponse createInventory(InventoryRequest inv) {
        try {
            Inventory inventory = Inventory.builder()
                    .quantity(inv.getQuantity())
                    .skuCode(inv.getSkuCode())
                    .build();
            inventoryRepository.save(inventory);
            log.info("Inventory Saved with the id : {}", inventory.getInventoryId());
            return createInventoryResponse(inventory);
        } catch (Exception e) {
            log.error("Error saving inventory: {}",  e.getMessage());
            throw new InventoryServiceBusinessException("Failed to create Inventory");
        }
    }

    private InventoryResponse createInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }

}

