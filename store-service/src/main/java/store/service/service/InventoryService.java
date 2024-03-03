package store.service.service;

import org.springframework.stereotype.Service;

import store.service.repository.InventoryRepository;

@Service
public class InventoryService {
	
	private  InventoryRepository  inventoryRepository;

	public InventoryService(InventoryRepository inventoryRepository) {
		super();
		this.inventoryRepository = inventoryRepository;
	}
	
	
	public String addInventory() {
		return "added....";
	}
	

}
