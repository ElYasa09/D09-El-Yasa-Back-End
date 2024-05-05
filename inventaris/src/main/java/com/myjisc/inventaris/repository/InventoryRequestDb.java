package com.myjisc.inventaris.repository;

// import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myjisc.inventaris.model.InventoryRequest;

@Transactional
@Repository
public interface InventoryRequestDb extends JpaRepository<InventoryRequest, UUID> {

    // TODO : Masih salah paramnya, liat tipe date di modelnya. cari lagi gimana
    // make jparepo

    // List<InventoryRequest> findAllByRequestByWaktuPermintaanDesc();
    // List<InventoryRequest>
    // findByWaktuPeminjamanBetweenAndIdPengirimanInOrderByWaktuPermintaan(
    // LocalDateTime requestDate, LocalDateTime returnDate, List<Long> idRequest);
}
