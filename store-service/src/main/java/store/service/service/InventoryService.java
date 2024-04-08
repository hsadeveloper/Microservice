package store.service.service;

import org.springframework.stereotype.Service;

import store.service.entity.Inventory;
import store.service.repository.InventoryRepository;

@Service
public class InventoryService {
	
	private  InventoryRepository  inventoryRepository;

	public InventoryService(InventoryRepository inventoryRepository) {
		super();
		this.inventoryRepository = inventoryRepository;
	}
	
	
	public Inventory getById(Long id) {

	    if (inventoryRepository.existsById(id)) {
	      return inventoryRepository.findById(id).get();
	    } else {
	      return null;
	    }
	  }
		
	public Inventory addInventory(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
	

}
