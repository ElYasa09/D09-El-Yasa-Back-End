package com.myjisc.kelas.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myjisc.kelas.model.Kelas;
import java.util.List;


public interface KelasDb extends JpaRepository<Kelas, UUID> {
    List<Kelas> findByIsDeletedFalse();
    Kelas findByIdKelasAndIsDeletedFalse(UUID idKelas);
}
