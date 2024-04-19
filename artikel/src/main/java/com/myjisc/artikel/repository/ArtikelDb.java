package com.myjisc.artikel.repository;

import com.myjisc.artikel.model.Artikel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ArtikelDb extends JpaRepository<Artikel, Long> {
        List<Artikel> findByIsDeletedFalse();
        Artikel findByIdArtikelAndIsDeletedFalse(Long id);
}
