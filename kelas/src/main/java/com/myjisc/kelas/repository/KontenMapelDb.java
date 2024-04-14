package com.myjisc.kelas.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myjisc.kelas.model.KontenMapel;
import java.util.List;


public interface KontenMapelDb extends JpaRepository<KontenMapel, UUID>{
    List<KontenMapel> findByMataPelajaranIdMapel(UUID idMapel);
} 
