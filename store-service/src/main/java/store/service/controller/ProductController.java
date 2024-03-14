package store.service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
		System.out.println("hellooooo");
        return productService.decrementQty(id, qty);
    }
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/")	
    public  ResponseEntity<?> createProduct(@Validated @RequestBody Product product,BindingResult bindingResult) {
		
		System.out.println("product--> "+product.toString());
		
		if(bindingResult.hasErrors()) {
			System.out.println("error ========>");
		}
		
		productService.saveProduct(product);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
		
	
        
    }

}
