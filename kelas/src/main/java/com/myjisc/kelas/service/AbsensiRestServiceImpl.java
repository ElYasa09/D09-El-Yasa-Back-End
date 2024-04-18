package com.myjisc.kelas.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myjisc.kelas.model.Absensi;
import com.myjisc.kelas.repository.AbsensiDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AbsensiRestServiceImpl implements AbsensiRestService{
    @Autowired
    private AbsensiDb absensiDb;

    @Override
    public Absensi createRestAbsensi(Absensi absensi) {
        absensiDb.save(absensi);
        return absensi;
    }

    @Override
    public List<Absensi> retrieveRestAllAbsensi() {
        return absensiDb.findAll();
    }  

    @Override
    public List<Absensi> retrieveRestAllAbsensiByKelas(UUID idKelas) {
        return absensiDb.findAllByKelasIdKelas(idKelas);
    }

    @Override
    public Absensi getRestAbsensiByIdAbsensi(UUID idAbsen) {
        for (Absensi absensi : retrieveRestAllAbsensi()) {
            if (absensi.getIdAbsen().equals(idAbsen)) {
                return absensi;
            }
        }
        return null;
    }
}
