package store.service.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import store.service.entity.ProductDTO;
import store.service.entity.ProductMapper;
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
	public List<ProductDTO> getProducts(){
	
		return productService.getProducts();
	}
	

	@GetMapping("/search-by-id/{queryId}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("queryId") Long queryId){
	
		Product product = productService.getProductById(queryId);
		ProductDTO productDTO = ProductMapper.toProductDTO(product);
        return ResponseEntity.ok(productDTO);
      
	}
	
	@GetMapping("/search-by-name/{query}")
	public List<Product> getProduct(@PathVariable("query") String query){
	
		return productService.searchProducts(query);
	}
	
	@GetMapping("/search-serial/{query}")
	public List<Product> getProductBYSerial(@PathVariable("query") String query){
		System.out.println("In controller search....."+query);	
		return productService.searchProducts(query);
	}
	
	@PutMapping("sell/{id}/{qty}")	
    public void decrementQTY(@PathVariable("id") Long id, @PathVariable("qty") int qty) {
		
         productService.decrementQty(id, qty);
    }
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/")	
    public  ResponseEntity<?> createProduct(@Validated @RequestBody ProductDTO productDTO) {
	System.out.println("inside post product");
	  productService.saveProduct(productDTO);
	  return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDTO);
		
	
        
    }

}
