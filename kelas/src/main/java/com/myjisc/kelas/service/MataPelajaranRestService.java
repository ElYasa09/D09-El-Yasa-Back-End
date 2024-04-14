package com.myjisc.kelas.service;

import java.rmi.NoSuchObjectException;
import java.util.UUID;

import com.myjisc.kelas.model.Kelas;
import com.myjisc.kelas.model.MataPelajaran;

public interface MataPelajaranRestService {
    MataPelajaran createRestMataPelajaran(MataPelajaran mataPelajaran);
    MataPelajaran updateRestMataPelajaran(MataPelajaran mataPelajaranDTO) throws NoSuchObjectException;
    MataPelajaran getRestMataPelajaranByIdMataPelajaran(UUID idMataPelajaran);
    Kelas getRestKelasByIdMataPelajaran(UUID idMataPelajaran);
    void deleteRestMapel(UUID idMataPelajaran) throws NoSuchObjectException;
}
