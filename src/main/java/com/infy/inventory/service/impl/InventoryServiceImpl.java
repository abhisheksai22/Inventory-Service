package com.infy.inventory.service.impl;

import com.infy.inventory.dto.InventoryRequest;
import com.infy.inventory.dto.InventoryResponse;
import com.infy.inventory.exception.InventoryNotFoundException;
import com.infy.inventory.exception.InventoryServiceBusinessException;
import com.infy.inventory.model.Inventory;
import com.infy.inventory.repo.InventoryRepository;
import com.infy.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<InventoryResponse> getAll() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        List<InventoryResponse> listOfInventoryResponse = inventoryList.stream()
                .map(inventory -> createInventoryResponse(inventory))
                .collect(Collectors.toList());
        if(listOfInventoryResponse.isEmpty()){
            throw new InventoryNotFoundException("Inventory is empty");
        }else {
            return listOfInventoryResponse;
        }
    }

    @Override
    public boolean inStock(String skuCode) {
        Optional<Inventory> isInventoryPresent = inventoryRepository.findBySkuCode(skuCode);
        if(isInventoryPresent.isPresent()) {
            return true;
        } else {
            return false;
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

