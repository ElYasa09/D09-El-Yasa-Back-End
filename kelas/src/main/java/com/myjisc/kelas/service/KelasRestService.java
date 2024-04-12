package com.myjisc.kelas.service;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

import com.myjisc.kelas.model.Kelas;

public interface KelasRestService {
    Kelas createRestKelas(Kelas kelas);
    Kelas updateRestKelas(Kelas kelasFromDTO) throws NoSuchObjectException;
    List<Kelas> retrieveRestAllKelas();
    List<Kelas> retrieveRestAvailableKelas();
    Kelas getRestKelasByIdKelas(UUID idKelas);
    void deleteRestKelas(UUID idKelas) throws NoSuchObjectException;
    Kelas getRestKelasByIdSiswa(Long idSiswa);
}
