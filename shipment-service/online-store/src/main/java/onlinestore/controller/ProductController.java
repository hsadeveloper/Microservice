package onlinestore.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import onlinestore.entities.Product;
import onlinestore.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductService productService;


  @GetMapping("/api")
  public String greet() {
    return "Hello, World!";
  }


  @GetMapping(path = "/all", produces = {"application/json", "application/xml"})

  public ResponseEntity<List<Product>> getProducts() {


    List<Product> products = new ArrayList<>();

    for (Product prod : productService.getProducts()) {

      products.add(prod);
    }
    System.out.println("I'm here ===" + products);

    return new ResponseEntity<>(products, HttpStatus.OK);

  }

  @GetMapping(path = "/{id}", produces = {"application/json", "application/xml"})

  public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {

    Product product = productService.getProduct(id);

    System.out.println("product" + product.getDepartment());

    return new ResponseEntity<>(product, HttpStatus.OK);

  }


  @PostMapping(path = "/new", consumes = {"application/json", "application/xml"},
      produces = {"application/json", "application/xml"})

  public ResponseEntity<Product> getProduct(@RequestBody Product product) {

    Product productObj = new Product();
    productObj.setDepartment(product.getDepartment());
    productObj.setDescription(product.getDescription());
    productObj.setManufacturer(product.getManufacturer());
    productObj.setProductName(product.getProductName());
    productObj.setProductId(product.getProductId());

    String name = product.getProductName();
    Long id = product.getProductId();
    if (!productService.getProductByName(name)) {

      productService.addOrUpdate(productObj);
    }

    return new ResponseEntity<Product>(product, HttpStatus.OK);
  }


  @PutMapping("/{id}/{qty}")
  public boolean decrementProduct(@PathVariable("id") Long id, @PathVariable("qty") int qty) {

    System.out.println("Id -->" + id + "  " + "qty ---> " + qty);
    return productService.decrementQty(id, qty);

  }


}

