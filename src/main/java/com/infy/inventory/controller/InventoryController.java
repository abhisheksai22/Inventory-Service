package com.infy.inventory.controller;

import com.infy.inventory.dto.APIResponse;
import com.infy.inventory.dto.InventoryRequest;
import com.infy.inventory.dto.InventoryResponse;
import com.infy.inventory.service.impl.InventoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/inventory-service")
public class InventoryController {

    public static final String SUCCESS = "Success";

    @Autowired
    private InventoryServiceImpl inventoryService;

    @PostMapping
    public ResponseEntity<APIResponse> createInventory(@RequestBody InventoryRequest inventoryRequest){
            log.info("Inv in controller: {}", inventoryRequest.toString());

            InventoryResponse inventoryResponse = inventoryService.createInventory(inventoryRequest);
            APIResponse<InventoryResponse> inventoryResponseDto = APIResponse
                    .<InventoryResponse>builder()
                    .status(SUCCESS)
                    .results(inventoryResponse)
                    .build();
            return new ResponseEntity<>(inventoryResponseDto,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllInventory(){
        log.info("Inventory Controller Get All");

        List<InventoryResponse> listOfInventory = inventoryService.getAll();
        APIResponse<List<InventoryResponse>> apiResponse = APIResponse
                .<List<InventoryResponse>>builder()
                .results(listOfInventory)
                .status(SUCCESS)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/sku-code")
    public ResponseEntity<APIResponse> inStock(@PathVariable("sku-code") String skuCode){
        boolean instock = inventoryService.inStock(skuCode);
    }
}
