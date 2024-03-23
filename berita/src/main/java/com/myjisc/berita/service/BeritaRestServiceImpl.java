package com.myjisc.berita.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.myjisc.berita.model.Berita;
import com.myjisc.berita.repository.BeritaDb;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class BeritaRestServiceImpl implements BeritaRestService{

    @Autowired
    private BeritaDb beritaDb;

    @Autowired
    private ImageUtilService imageUtilService;

    @Override
    public Berita createRestBerita(Berita berita) {
        var timeNow = LocalDateTime.now();
        berita.setDateCreated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
        berita.setDateUpdated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
        beritaDb.save(berita);
        return berita;
    }

    @Override
    public Berita createRestBerita(Berita berita, MultipartFile file) throws IOException {
        if (!checkFile(file)) {
            throw new IOException("File is not an image");
        }
        
        var timeNow = LocalDateTime.now();

        berita.setImageBerita(setRestBeritaImage(berita, file));
        berita.setDateCreated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
        berita.setDateUpdated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
        beritaDb.save(berita);
        return berita;
    }

    @Override
    public byte[] setRestBeritaImage(Berita berita, MultipartFile file) throws IOException{
        byte[] imageBerita = imageUtilService.compressImage(file.getBytes());
        return imageBerita;
    }

    @Override
    public List<Berita> retrieveRestAllBerita() {
        return beritaDb.findAll();
    }

    @Override
    public List<Berita> retrieveRestAvailableBerita() { return beritaDb.findByIsDeletedFalse(); }

    @Override
    public Berita getRestBeritaById(long id) {
        return beritaDb.findByIdBeritaAndIsDeletedFalse(id);
    }

    @Override
    public void deleteRestBerita(Berita berita) {
        berita.setDeleted(true);
        beritaDb.save(berita);
    }

    @Override
    public Berita updateRestBerita(Berita beritaFromDTO) throws NoSuchObjectException {
        var berita = beritaDb.findByIdBeritaAndIsDeletedFalse(beritaFromDTO.getIdBerita());
        if (berita == null) {
            throw new NoSuchObjectException("Berita not found");
        } else {
            var timeNow = LocalDateTime.now();
            berita.setDateUpdated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
            berita.setJudulBerita(beritaFromDTO.getJudulBerita());
            berita.setIsiBerita(beritaFromDTO.getIsiBerita());
            berita.setKategori(beritaFromDTO.getKategori());
            beritaDb.save(berita);

            return berita;
        }
    }

    @Override
    public Berita updateRestBerita(Berita beritaFromDTO, MultipartFile file) throws IOException {
        var berita = beritaDb.findByIdBeritaAndIsDeletedFalse(beritaFromDTO.getIdBerita());
        if (berita == null) {
            throw new NoSuchObjectException("Berita not found");
        } else {
            if (!checkFile(file)) {
                throw new IOException("File is not an image");
            }
            var timeNow = LocalDateTime.now();
            berita.setDateUpdated(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
            berita.setJudulBerita(beritaFromDTO.getJudulBerita());
            berita.setIsiBerita(beritaFromDTO.getIsiBerita());
            berita.setImageBerita(setRestBeritaImage(beritaFromDTO, file));
            berita.setKategori(beritaFromDTO.getKategori());
            beritaDb.save(berita);

            return berita;
        }
    }

    @Override
    public byte[] getImageBerita (Long id) throws NoSuchObjectException {
        var berita = beritaDb.findByIdBeritaAndIsDeletedFalse(id);

        if (berita != null ) {
            byte[] image = imageUtilService.decompressImage(berita.getImageBerita());

            if (image == null) {
                throw new NoSuchObjectException("Image Not Found");
            }

            return image;
        } else {
            throw new NoSuchObjectException("Berita Not Found");
        }
    }

    @Override 
    public boolean checkFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }
}
