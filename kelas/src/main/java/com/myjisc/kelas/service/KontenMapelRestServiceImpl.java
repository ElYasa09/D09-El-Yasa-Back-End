package com.myjisc.kelas.service;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myjisc.kelas.model.KontenMapel;
import com.myjisc.kelas.repository.KontenMapelDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class KontenMapelRestServiceImpl implements KontenMapelRestService{
    @Autowired
    private KontenMapelDb kontenMapelDb;
    
    @Override
    public KontenMapel createRestKontenMapel(KontenMapel kontenMapel) {
        kontenMapelDb.save(kontenMapel);
        return kontenMapel;
    }

    @Override
    public KontenMapel creteRestKontenMapel(KontenMapel kontenMapel, MultipartFile file) throws IOException {
        kontenMapel.setNamaFile(file.getOriginalFilename());
        kontenMapel.setFileKonten(file.getBytes());
        kontenMapel.setTipeFile(file.getContentType());
        kontenMapelDb.save(kontenMapel);
        return kontenMapel;
    }
    
    @Override
    public KontenMapel getKontenMapelByIdKonten(UUID idKonten) {
        return kontenMapelDb.findById(idKonten).orElse(null);
    }
    
    @Override
    public List<KontenMapel> getKontenMapelByIdMapel(UUID idMapel) {
        return kontenMapelDb.findByMataPelajaranIdMapel(idMapel);
    }
    
    @Override
    public void deleteKontenMapel(UUID idKonten) throws NoSuchObjectException {
        var materi = kontenMapelDb.findById(idKonten).get();

        if (materi != null) {
            kontenMapelDb.delete(materi);
        } else {
            throw new NoSuchObjectException("Konten Not Found");
        }
    }
}
