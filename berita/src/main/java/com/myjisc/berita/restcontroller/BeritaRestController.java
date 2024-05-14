package com.myjisc.berita.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.myjisc.berita.dto.BeritaMapper;
import com.myjisc.berita.dto.request.CreateBeritaRequestDTO;
import com.myjisc.berita.dto.request.UpdateBeritaRequestDTO;
import com.myjisc.berita.model.Berita;
import com.myjisc.berita.service.BeritaRestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/berita")
public class BeritaRestController {
    @Autowired
    BeritaRestService beritaRestService;

    @Autowired
    BeritaMapper beritaMapper;

    @PostMapping("/create")
    public ResponseEntity createBerita(@Valid @RequestBody @ModelAttribute CreateBeritaRequestDTO beritaRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile file, BindingResult bindingResult)
            throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        try {
            if (file != null) {
                var beritaFromDTO = beritaMapper.createRestBeritaDTOToBerita(beritaRequestDTO);
                var berita = beritaRestService.createRestBerita(beritaFromDTO, file);
    
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
    
                Map<String, Object> data = new HashMap<>();
                data.put("idBerita", berita.getIdBerita());
                data.put("judulBerita", berita.getJudulBerita());
                data.put("isiBerita", berita.getIsiBerita());
                data.put("imageBerita", "image successfully uploaded");
                data.put("kategori", berita.getKategori());
                data.put("dateCreated", berita.getDateCreated());
                data.put("dateUpdated", berita.getDateUpdated());
                data.put("deleted", berita.isDeleted());

                responseBody.put("data", data);
    
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else {
                var beritaFromDTO = beritaMapper.createRestBeritaDTOToBerita(beritaRequestDTO);
                var berita = beritaRestService.createRestBerita(beritaFromDTO);
    
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("data", berita);
    
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/view-all")
    public ResponseEntity viewAllBerita() {

        List<Berita> listAvailableBerita = beritaRestService.retrieveRestAvailableBerita();

        if (listAvailableBerita.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        try {
            List<Map<String, Object>> beritaDataList = new ArrayList<>();
            for (Berita berita : listAvailableBerita) {
                Map<String, Object> beritaData = new HashMap<>();
                beritaData.put("idBerita", berita.getIdBerita());
                beritaData.put("judulBerita", berita.getJudulBerita());
                beritaData.put("isiBerita", berita.getIsiBerita());
                beritaData.put("imageBerita", "/api/berita/" + berita.getIdBerita() + "/image");
                beritaData.put("kategori", berita.getKategori());
                beritaData.put("dateCreated", berita.getDateCreated());
                beritaData.put("dateUpdated", berita.getDateUpdated());
                beritaData.put("deleted", berita.isDeleted());
    
                beritaDataList.add(beritaData);
            }
    
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", beritaDataList);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/{idBerita}")
    public ResponseEntity viewBerita(@PathVariable("idBerita") Long idBerita) {
        try {
            var berita = beritaRestService.getRestBeritaById(idBerita);

            if (berita == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Berita not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idBerita", berita.getIdBerita());
            data.put("judulBerita", berita.getJudulBerita());
            data.put("isiBerita", berita.getIsiBerita());
            data.put("imageBerita", "/api/berita/" + berita.getIdBerita() + "/image");
            data.put("kategori", berita.getKategori());
            data.put("dateCreated", berita.getDateCreated());
            data.put("dateUpdated", berita.getDateUpdated());
            data.put("deleted", berita.isDeleted());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateBerita(@PathVariable("id") Long id,
            @Valid @RequestBody @ModelAttribute UpdateBeritaRequestDTO beritaRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile file, BindingResult bindingResult)
            throws IOException {
        
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        try {
            if (file != null) {
                var beritaFromDTO = beritaMapper.updateRestBeritaDTOToBerita(beritaRequestDTO);
                beritaFromDTO.setIdBerita(id);
                var berita = beritaRestService.updateRestBerita(beritaFromDTO, file);
    
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
    
                Map<String, Object> data = new HashMap<>();
                data.put("idBerita", berita.getIdBerita());
                data.put("judulBerita", berita.getJudulBerita());
                data.put("isiBerita", berita.getIsiBerita());
                data.put("imageBerita", "image successfully uploaded");
                data.put("kategori", berita.getKategori());
                data.put("dateCreated", berita.getDateCreated());
                data.put("dateUpdated", berita.getDateUpdated());
                data.put("deleted", berita.isDeleted());

                responseBody.put("data", data);
    
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else {
                var beritaFromDTO = beritaMapper.updateRestBeritaDTOToBerita(beritaRequestDTO);
                beritaFromDTO.setIdBerita(id);
                var berita = beritaRestService.updateRestBerita(beritaFromDTO);
    
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
                responseBody.put("data", berita);
    
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

       
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity getBeritaImage(@PathVariable("id") Long id) {
        try {
            byte[] image = beritaRestService.getImageBerita(id);
            
            if (image == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Image not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBerita(@PathVariable("id") Long id) {
        try {
            var berita = beritaRestService.getRestBeritaById(id);

            if (berita == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Berita not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            beritaRestService.deleteRestBerita(berita);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Berita has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
    
}
