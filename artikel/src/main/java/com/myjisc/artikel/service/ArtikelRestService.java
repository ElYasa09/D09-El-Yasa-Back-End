package com.myjisc.artikel.service;

import com.myjisc.artikel.dto.request.CreateArtikelRequestDTO;
import com.myjisc.artikel.model.Artikel;
import com.myjisc.artikel.repository.ArtikelDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional
public class ArtikelRestService {

    @Autowired
    private ArtikelDb artikelDb;

    @Autowired
    private ImageUtil imageUtil;

    public List<Artikel> retreiveAllArtikel() {
        return artikelDb.findAll();
    }

    public List<Artikel> retreiveAvailableArtikel() {
        return artikelDb.findByIsDeletedFalse();
    }

    public Artikel getArtikelByID(String idArtikel)  {
        return artikelDb.findByIdArtikelAndIsDeletedFalse(Long.valueOf(idArtikel));
    }

    public Artikel createRestArtikel (Artikel artikel) {
        var timeNow = LocalDateTime.now();
        artikel.setDateCreated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
        artikelDb.save(artikel);
        return  artikel;
    }

    public Artikel createRestArtikel (Artikel artikel, MultipartFile file) throws IOException {
        if (checkFile(file)) {
            artikel.setImageArtikel(imageUtil.compressImage(file.getBytes()));
            var timeNow = LocalDateTime.now();
            artikel.setDateCreated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
            artikelDb.save(artikel);
            return artikel;
        } else {
            throw new IOException("File is not an image");
        }

    }

    public byte[] getImage (String idArtikel) throws NoSuchObjectException {
        Artikel existingArtikel = artikelDb.findByIdArtikelAndIsDeletedFalse(Long.valueOf(idArtikel));

        if (existingArtikel != null) {
            byte[] image = imageUtil.decompressImage(existingArtikel.getImageArtikel());
            return image;
        } else {
            throw new NoSuchObjectException("Article not found");
        }

    }

    public void deleteArtikel(Artikel artikel) {
        artikel.setDeleted(true);
        artikelDb.save(artikel);
    }

    public boolean checkFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }
}
