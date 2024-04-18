package com.myjisc.kelas.service;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.myjisc.kelas.model.KontenMapel;

public interface KontenMapelRestService {
    KontenMapel createRestKontenMapel(KontenMapel kontenMapel);
    KontenMapel creteRestKontenMapel(KontenMapel kontenMapel, MultipartFile file) throws IOException;
    KontenMapel getKontenMapelByIdKonten(UUID idKonten);
    List<KontenMapel> getKontenMapelByIdMapel(UUID idMapel);
    void deleteKontenMapel(UUID idKonten) throws NoSuchObjectException;
}
