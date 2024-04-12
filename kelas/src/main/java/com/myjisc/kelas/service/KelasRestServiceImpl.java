package com.myjisc.kelas.service;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myjisc.kelas.model.Kelas;
import com.myjisc.kelas.repository.KelasDb;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class KelasRestServiceImpl implements KelasRestService {

    @Autowired
    private KelasDb kelasDb;


    @Override
    public Kelas createRestKelas(Kelas kelas) {
        kelasDb.save(kelas);
        return kelas;
    }

    @Override
    public Kelas updateRestKelas(Kelas kelasFromDTO) throws NoSuchObjectException {
        var kelas = kelasDb.findByIdKelasAndIsDeletedFalse(kelasFromDTO.getIdKelas());
        if (kelas == null) {
            throw new NoSuchObjectException("Kelas not found");
        } else {
            kelas.setNamaKelas(kelasFromDTO.getNamaKelas());
            kelas.setDeskripsiKelas(kelasFromDTO.getDeskripsiKelas());
            kelas.setNuptkWaliKelas(kelasFromDTO.getNuptkWaliKelas());
            kelas.setNisnSiswa(kelasFromDTO.getNisnSiswa());

            kelasDb.save(kelas);

            return kelas;
        }
    }
    
    @Override
    public List<Kelas> retrieveRestAllKelas() {
        return kelasDb.findAll();
    }
    
    @Override
    public List<Kelas> retrieveRestAvailableKelas() {
        return kelasDb.findByIsDeletedFalse();
    }
    
    @Override
    public Kelas getRestKelasByIdKelas(UUID idKelas) {
        return kelasDb.findByIdKelasAndIsDeletedFalse(idKelas);
    }
    
    @Override
    public void deleteRestKelas(UUID idKelas) throws NoSuchObjectException {
        var kelas = kelasDb.findByIdKelasAndIsDeletedFalse(idKelas);
        if (kelas == null) {
            throw new NoSuchObjectException("Kelas not found");
        } else {
            kelas.setDeleted(true);
            kelasDb.save(kelas);
        }
    }
    
    @Override
    public Kelas getRestKelasByIdSiswa(Long idSiswa) {
        List<Kelas> listKelasExisting = kelasDb.findByIsDeletedFalse();
        if (idSiswa != null) {
            for (Kelas kelas : listKelasExisting) {
                if (kelas.getNisnSiswa().contains(idSiswa)) {
                    return kelas;
                }
            }
            throw new NoResultException("Cant find kelas");
        }

        return null;
    }
}
