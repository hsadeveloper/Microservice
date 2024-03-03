package store.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import store.service.entity.Inventory;
import store.service.service.InventoryService;

@RequestMapping("/inventroy")
@RestController
public class InventoryController {
	
	private InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		super();
		this.inventoryService = inventoryService;
	}
	
	@GetMapping("/test")
	public String String () {
		return "added";
	}
	
	@PostMapping("/")
	public  String createInventory (@RequestBody Inventory inventory) {
		System.out.println("I'm inside createInventory");
		return "added";
	}

}
