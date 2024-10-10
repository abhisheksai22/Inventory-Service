package com.infy.inventory.service;

import com.infy.inventory.dto.InventoryRequest;
import com.infy.inventory.dto.InventoryResponse;
import com.infy.inventory.model.Inventory;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest inv);

}
