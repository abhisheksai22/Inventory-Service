package com.infy.inventory.controller;

import com.infy.inventory.InventoryApplication;
import com.infy.inventory.dto.APIResponse;
import com.infy.inventory.dto.InventoryRequest;
import com.infy.inventory.dto.InventoryResponse;
import com.infy.inventory.model.Inventory;
import com.infy.inventory.service.impl.InventoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/inventory-service")
public class InventoryController {

    public static final String SUCCESS = "Success";

    @Autowired
    private InventoryServiceImpl inventoryService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createInventory(@RequestBody InventoryRequest inventoryRequest){
            log.info("Inv in controller: {}", inventoryRequest.getEmail());

            InventoryResponse inventoryResponse = inventoryService.createInventory(inventoryRequest);
            APIResponse<InventoryResponse> inventoryResponseDto = APIResponse
                    .<InventoryResponse>builder()
                    .status(SUCCESS)
                    .results(inventoryResponse)
                    .build();
            return new ResponseEntity<>(inventoryResponseDto,HttpStatus.OK);

    }

}
