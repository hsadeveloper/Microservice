package store.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.service.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository <Inventory,Long> {

}
