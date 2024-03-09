package com.myjisc.berita.service;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.NoSuchElementException;

import com.myjisc.berita.model.Berita;
import org.springframework.web.multipart.MultipartFile;

public interface BeritaRestService {
    Berita createRestBerita(Berita berita);
    Berita createRestBerita(Berita berita, MultipartFile file) throws IOException;
    List<Berita> retrieveRestAllBerita();
    List<Berita> retrieveRestAvailableBerita();
    Berita getRestBeritaById(long id);
    void deleteRestBerita(Berita berita);
    Berita updateRestBerita(Berita beritaFromDTO) throws NoSuchElementException, NoSuchObjectException;
    Berita updateRestBerita(Berita beritaFromDTO, MultipartFile file) throws IOException;
    byte[] setRestBeritaImage(Berita berita, MultipartFile file) throws IOException;
    byte[] getImageBerita (Long id) throws NoSuchObjectException;
    boolean checkFile(MultipartFile file) throws IOException;
}
