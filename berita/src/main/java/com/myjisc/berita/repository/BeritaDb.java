package com.myjisc.berita.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myjisc.berita.model.Berita;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BeritaDb extends JpaRepository<Berita, Long> {
    List<Berita> findByIsDeletedFalse();
    Berita findByIdBeritaAndIsDeletedFalse(Long idBerita);
}
