package com.myjisc.kelas.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myjisc.kelas.model.KontenMapel;

public interface KontenMapelDb extends JpaRepository<KontenMapel, UUID>{
}
