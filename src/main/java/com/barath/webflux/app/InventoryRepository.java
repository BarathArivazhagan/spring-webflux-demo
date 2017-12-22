package com.barath.webflux.app;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by barath on 22/12/17.
 */
public interface InventoryRepository  extends JpaRepository<Inventory,Long>{

    List<Inventory> findByProductName(String productName);

    List<Inventory> findByLocationName(String locationName);
}
