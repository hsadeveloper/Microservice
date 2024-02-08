package store.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import store.service.entity.Product;
import store.service.service.ProductService;

@RequestMapping("/product")
@RestController
public class ProductController {
	
	ProductService  productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@GetMapping("/")
	public String TestController() {
		return "Prduct controller is working";
	}
	
	@GetMapping("/all")	
	public List<Product> getProducts(){
		return productService.getProducts();
	}
	
	@PutMapping("/{id}/{qty}")	
    public Product decrementQTY(@PathVariable("id") Long id, @PathVariable("qty") int qty) {
        return productService.decrementQty(id, qty);
    }
	

}
