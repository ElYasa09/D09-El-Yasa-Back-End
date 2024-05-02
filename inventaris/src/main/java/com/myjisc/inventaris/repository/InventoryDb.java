package com.myjisc.inventaris.repository;

import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myjisc.inventaris.model.Inventory;

@Repository
@Transactional
public interface InventoryDb extends JpaRepository<Inventory, UUID> {
    
}
