package store.service.repository;



import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import store.service.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query(value = "select * from product where product_name like :value", nativeQuery = true)
  List<Product> getByproductName(String value);

  Optional<Product> findByProductNameAndDescription(String name, String desc);


}
