package com.myjisc.inventaris.repository;

import java.util.UUID;
import java.util.List;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myjisc.inventaris.model.NotifMessage;

@Repository
@Transactional
public interface NotifMessageDb extends JpaRepository<NotifMessage, UUID> {
    List<NotifMessage> findAllByIdPeminjam(Long idPeminjam);

}
