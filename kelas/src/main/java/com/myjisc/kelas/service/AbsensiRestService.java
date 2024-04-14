package com.myjisc.kelas.service;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

import com.myjisc.kelas.model.Absensi;

public interface AbsensiRestService {
    Absensi createRestAbsensi(Absensi absensi);
    List<Absensi> retrieveRestAllAbsensi();
    Absensi getRestAbsensiByIdAbsensi(UUID idAbsen);
}
