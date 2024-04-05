package store.service.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.service.entity.Product;
import store.service.repository.ProductRepository;


@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public boolean saveProduct(Product product) {
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
	  return (List<Product>) productRepository.searchByProductName(searchName);
	 
		
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
