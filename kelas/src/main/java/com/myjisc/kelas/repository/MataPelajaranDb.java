package com.myjisc.kelas.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myjisc.kelas.model.MataPelajaran;

public interface MataPelajaranDb extends JpaRepository<MataPelajaran, UUID> {

}
