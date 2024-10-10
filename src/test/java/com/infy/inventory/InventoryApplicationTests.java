package com.infy.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.inventory.dto.InventoryRequest;
import com.infy.inventory.repo.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.net.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Slf4j
class InventoryApplicationTests {
	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.28")
			.withDatabaseName("hotel")
			.withUsername("root")
        	.withPassword("773726");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private InventoryRepository inventoryRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
	}

	static  {
		mySQLContainer.start();
	}

	@Test
	@Disabled
	void shouldCreateInventory() throws Exception {
		InventoryRequest inventoryRequest = getInventoryRequest();
		log.info("Inventory Request : {}", inventoryRequest);
		String inventoryRequestString = objectMapper.writeValueAsString(inventoryRequest);
		log.info("Inventory Request String: {}", inventoryRequestString);
		mockMvc.perform(MockMvcRequestBuilders.post("/inventory-service/create")
				.contentType(MediaType.JSON_UTF_8.type())
				.content(inventoryRequestString))
				.andExpect(status().isOk());
		Assertions.assertEquals(1, inventoryRepository.findAll().size());
	}

	private InventoryRequest getInventoryRequest() {
		return InventoryRequest.builder()
				.email("Abhishek@gmail.com")
				.name("Abhishek Sai")
				.build();
	}

}
