package com.myjisc.kelas.service;

import java.rmi.NoSuchObjectException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myjisc.kelas.model.Kelas;
import com.myjisc.kelas.model.MataPelajaran;
import com.myjisc.kelas.repository.MataPelajaranDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MataPelajaranRestServiceImpl implements MataPelajaranRestService {

    @Autowired
    private MataPelajaranDb mataPelajaranDb;


    public MataPelajaran createRestMataPelajaran(MataPelajaran mataPelajaran) {
        mataPelajaranDb.save(mataPelajaran);
        return mataPelajaran;
    }

    public MataPelajaran updateRestMataPelajaran(MataPelajaran mataPelajaranDTO) throws NoSuchObjectException {
        var mataPelajaran = mataPelajaranDb.findById(mataPelajaranDTO.getIdMapel()).get();
        if (mataPelajaran == null) {
            throw new NoSuchObjectException("Kelas not found");
        } else {
            mataPelajaran.setNamaMapel(mataPelajaranDTO.getNamaMapel());
            mataPelajaran.setNuptkGuruMengajar(mataPelajaranDTO.getNuptkGuruMengajar());

            mataPelajaranDb.save(mataPelajaran);
            return mataPelajaran;
        }
    }

    public MataPelajaran getRestMataPelajaranByIdMataPelajaran(UUID idMataPelajaran) { 
        return mataPelajaranDb.findById(idMataPelajaran).get();
    }

    public Kelas getRestKelasByIdMataPelajaran(UUID idMataPelajaran) {
        return mataPelajaranDb.findById(idMataPelajaran).get().getKelas();
    }

    public void deleteRestMapel(UUID idMataPelajaran) throws NoSuchObjectException {
        var mataPelajaran = mataPelajaranDb.findById(idMataPelajaran).get();
        if (mataPelajaran == null) {
            throw new NoSuchObjectException("Mata Pelajaran not found");
        } else {
            mataPelajaranDb.delete(mataPelajaran);
        }
    }
}
