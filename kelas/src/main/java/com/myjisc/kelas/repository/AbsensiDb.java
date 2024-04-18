package com.myjisc.kelas.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myjisc.kelas.model.Absensi;

public interface AbsensiDb extends JpaRepository<Absensi, UUID>{
    List<Absensi> findAllByKelasIdKelas(UUID idKelas);
}