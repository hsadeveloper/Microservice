package store.service.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import store.service.entity.Inventory;
import store.service.entity.Product;
import store.service.entity.ProductDTO;
import store.service.entity.ProductMapper;
import store.service.entity.ProductNotFoundException;
import store.service.repository.InventoryRepository;
import store.service.repository.ProductRepository;


@Service
public class ProductService {


  private ProductRepository productRepository;
  
  private DepartmentService departmentService;
  
  private InventoryService inventoryService;
  
  private InventoryRepository inventoryRepository;
  
 
  public ProductService(ProductRepository productRepository, DepartmentService departmentService,
		InventoryService inventoryService, InventoryRepository inventoryRepository) {
	super();
	this.productRepository = productRepository;
	this.departmentService = departmentService;
	this.inventoryService = inventoryService;
	this.inventoryRepository = inventoryRepository;
}

public boolean saveProduct(ProductDTO productDTO) {
	
     System.out.println(productDTO + "product service");
     
     Product product = new Product();
     product.setProductName(productDTO.getProductName());
     product.setDescription(productDTO.getDescription());
     product.setProductPrice(productDTO.getProductPrice());
     product.setSerialNumber(productDTO.getSerialNumber());
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

  public List<ProductDTO> getProducts() {
	  
    List <Product>  products =  productRepository.findAll();
    
    return products.stream()
            .map(ProductMapper::toProductDTO)
            .collect(Collectors.toList());
   
  }

  public Product getProductById(Long id) {
 
    return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("**** Error**** Product with ID " + id + " not found"));
  }

  public boolean getProductByName(String name) {

	  return productRepository.getByProductName(name)
		        .map(products -> !products.isEmpty())
		        .orElse(false);


  }

  public boolean getProductByNameAndDescription(String name, String desc) {
    Optional<Product> product = productRepository.findByProductNameAndDescription(name, desc);
    if (product.isPresent()) {
      return true;
    }
    return false;

  }
  
  public List<Product> searchProducts(String  searchName) {

	  return productRepository.searchByProductName(searchName);
	 
		
	}

  
  public  Product searchProductByserialNumber(String  serialNumber) {
	  System.out.println(serialNumber);
	  
	  return productRepository.searchBySerialNumber(serialNumber);
	 
		
	}
  
  public void decrementQty(Long Id, int qty) {

    Optional<Product> optionalProduct = productRepository.findById(Id);
    System.out.println("inside sell controller  "+optionalProduct);

    if (optionalProduct.isPresent()) {

       Product product = optionalProduct.get();  
       int total = product.getInventory().getQty();    
       Inventory inventory = product.getInventory();
       inventory.setQty(total - qty);
       inventory.setQtySold( inventory.getQtySold() + qty );   
       inventoryRepository.save(inventory);
    }
  
  }

}
