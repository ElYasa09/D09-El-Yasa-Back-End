package com.myjisc.inventaris.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myjisc.inventaris.model.InventoryRequest;

@Repository
public interface InventoryRequestDb extends JpaRepository<InventoryRequest, UUID> {
    List<InventoryRequest> findAllByRequestByWaktuPermintaanDesc();
    List<InventoryRequest> findByWaktuPeminjamanBetweenAndIdPengirimanInOrderByWaktuPermintaan(LocalDateTime requestDate, LocalDateTime returnDate, List<Long> idRequest);

}


