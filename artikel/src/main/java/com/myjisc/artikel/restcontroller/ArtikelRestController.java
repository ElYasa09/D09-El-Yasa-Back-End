package com.myjisc.artikel.restcontroller;


import com.myjisc.artikel.dto.ArtikelMapper;
import com.myjisc.artikel.dto.request.CreateArtikelRequestDTO;
import com.myjisc.artikel.model.Artikel;
import com.myjisc.artikel.service.ArtikelRestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/artikel")
public class ArtikelRestController {

    @Autowired
    private ArtikelRestService artikelRestService;

    @Autowired
    private ArtikelMapper artikelMapper;

    @GetMapping(value = "/view-all")
    public ResponseEntity restGetAllArtikel() {
        List<Artikel> listArtikel = artikelRestService.retreiveAvailableArtikel();

        if (listArtikel.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        try {
            List<Map<String, Object>> artikelDataList = new ArrayList<>();
            for (Artikel artikel : listArtikel) {
                Map<String, Object> artikelData = new HashMap<>();
                artikelData.put("idArtikel", artikel.getIdArtikel());
                artikelData.put("judulArtikel", artikel.getJudulArtikel());
                artikelData.put("isiArtikel", artikel.getIsiArtikel());
                artikelData.put("imageArtikel", "/api/artikel/" + artikel.getIdArtikel() + "/image");
                artikelData.put("kategori", artikel.getKategori());
                artikelData.put("dateCreated", artikel.getDateCreated());
                artikelData.put("deleted", artikel.isDeleted());
    
                artikelDataList.add(artikelData);
            }
    
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", artikelDataList);
    
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity getArtikelImage(@PathVariable("id") String id) {
        try {
            byte[] image = artikelRestService.getImage(id);

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

    @PostMapping(value = "/create")
    public ResponseEntity restCreateArtikel(@Valid @RequestBody @ModelAttribute CreateArtikelRequestDTO createArtikelDTO,
                                            @RequestPart(value = "image", required = false) MultipartFile file, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasFieldErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } else {
            try {
                if (file != null) {
                    var artikelFromDTO = artikelMapper.createArtikelRequestDTOToArtikel(createArtikelDTO);
                    var artikel = artikelRestService.createRestArtikel(artikelFromDTO, file);

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("status", "success");
                    
                    Map<String, Object> data = new HashMap<>();
                    data.put("idArtikel", artikel.getIdArtikel());
                    data.put("judulArtikel", artikel.getJudulArtikel());
                    data.put("isiArtikel", artikel.getIsiArtikel());
                    data.put("imageArtikel", "image success uploaded");
                    data.put("kategori", artikel.getKategori());
                    data.put("dateCreated", artikel.getDateCreated());
                    data.put("deleted", artikel.isDeleted());

                    responseBody.put("data", data);

                    return ResponseEntity.status(HttpStatus.OK).body(responseBody);

                } else {
                    var artikelFromDTO = artikelMapper.createArtikelRequestDTOToArtikel(createArtikelDTO);
                    var artikel = artikelRestService.createRestArtikel(artikelFromDTO);

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("status", "success");
                    responseBody.put("data", artikel);

                    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
                }

            } catch (IOException e) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Check your input again");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }
        }
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity deleteArtikel(@PathVariable("id") String id) {
        try {
            var artikel = artikelRestService.getArtikelByID(id);

            if (artikel == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Artikel not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            artikelRestService.deleteArtikel(artikel);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            responseBody.put("data", "null");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity viewArtikel(@PathVariable("id") String idArtikel) {
        try {
            var artikel = artikelRestService.getArtikelByID(idArtikel);
    
            if (artikel == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Artikel not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }
    
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            

            Map<String, Object> data = new HashMap<>();
            data.put("idArtikel", artikel.getIdArtikel());
            data.put("judulArtikel", artikel.getJudulArtikel());
            data.put("isiArtikel", artikel.getIsiArtikel());
            data.put("imageArtikel", "/api/artikel/" + artikel.getIdArtikel() + "/image");
            data.put("kategori", artikel.getKategori());
            data.put("dateCreated", artikel.getDateCreated());
            data.put("deleted", artikel.isDeleted());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
    
}
