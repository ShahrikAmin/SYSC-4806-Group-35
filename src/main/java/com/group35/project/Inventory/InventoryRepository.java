package com.group35.project.Inventory;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    List<Inventory> findBySize(int size);
    Inventory findById(long id);


}
