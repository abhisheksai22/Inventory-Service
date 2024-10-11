package com.infy.inventory.service;

import com.infy.inventory.dto.InventoryRequest;
import com.infy.inventory.dto.InventoryResponse;
import com.infy.inventory.model.Inventory;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest inv);

    List<InventoryResponse> getAll();

    boolean inStock(String skuCode);
}
