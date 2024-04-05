package store.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.service.entity.Manufacturer;

@Repository
public interface ManufactureRepository extends JpaRepository<Manufacturer, Long> {

}
