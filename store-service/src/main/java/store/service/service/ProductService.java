package store.service.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import store.service.entity.Inventory;
import store.service.entity.Product;
import store.service.entity.ProductDTO;
import store.service.repository.ProductRepository;


@Service
public class ProductService {


  private ProductRepository productRepository;
  
  private DepartmentService departmentService;
  
  private InventoryService inventoryService;
  
 
  public ProductService(ProductRepository productRepository, DepartmentService departmentService,
		InventoryService inventoryService) {
	super();
	this.productRepository = productRepository;
	this.departmentService = departmentService;
	this.inventoryService = inventoryService;
}

public boolean saveProduct(ProductDTO productDTO) {
     System.out.println(productDTO);
     
     Product product = new Product();
     product.setProductName(productDTO.getProductName());
     product.setDescription(productDTO.getDescription());
     product.setProductPrice(productDTO.getProductPrice());

     if(productDTO.getInventoryId()!= null) {  
    	Inventory inventory = inventoryService.getById(productDTO.getInventoryId());
    	product.setInventory(inventory); 
     }     
    if(productDTO.getDepartmentId()!= null) {  	 
	 product.setDepartment(departmentService.getDepartentById(productDTO.getDepartmentId())); 
    }
    
   
    product.getManufacturers().add(product.getInventory().getManufacturer());
    productRepository.save(product);
    return true;
  }

  public List<Product> getProducts() {
    return productRepository.findAll();
  }

  public Product getProduct(Long Id) {
    Optional<Product> product = productRepository.findById(Id);
    if (product.isPresent()) {
      return product.get();
    }
    return null;
  }

  public boolean getProductByName(String name) {

    List<Product> product = productRepository.getByproductName(name);
    if (product.size() > 0) {
      return true;
    }
    return false;

  }

  public boolean getProductByNameAndDescription(String name, String desc) {
    Optional<Product> product = productRepository.findByProductNameAndDescription(name, desc);
    if (product.isPresent()) {
      return true;
    }
    return false;

  }
  
  public List<Product> searchProducts(String  searchName) {
	  System.out.println("In service search.....");
	  return productRepository.searchByProductName(searchName);
	 
		
	}

  public Product decrementQty(Long Id, int qty) {

    Optional<Product> optionalProduct = productRepository.findById(Id);

    if (optionalProduct.isPresent()) {

      Product product = optionalProduct.get();
     // int total = product.getQty();
      
      //System.out.println("total---> " + total + "total - qty --> " + (total - qty));
      
//      product.setQty(total - qty);
//      Product savedProduct = productRepository.save(product);
//      return savedProduct;
    }
    return optionalProduct.get();
  }



}
