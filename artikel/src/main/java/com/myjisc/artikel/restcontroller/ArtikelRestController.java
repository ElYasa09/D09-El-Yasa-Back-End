package com.myjisc.artikel.restcontroller;


import com.myjisc.artikel.dto.request.CreateArtikelRequestDTO;
import com.myjisc.artikel.dto.response.ArtikelMapper;
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
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/artikel")
public class ArtikelRestController {

    @Autowired
    private ArtikelRestService artikelRestService;

    @GetMapping(value = "/view-all")
    public ResponseEntity restGetAllArtikel() {
        List<Artikel> listArtikel = artikelRestService.retreiveAvailableArtikel();
        try {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, List<Artikel>> data = new HashMap<>();
            data.put("artikel", listArtikel);

            responseBody.put("data", data);

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
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity restCreateArtikel(@Valid @RequestBody @ModelAttribute CreateArtikelRequestDTO createArtikelDTO,
                                            @RequestPart(value = "image", required = false)MultipartFile file, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasFieldErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } else {
            try {
                var artikel = artikelRestService.createRestArtikel(createArtikelDTO, file);

                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");

                responseBody.put("data", artikel);

                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } catch (IOException e) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Can't process image");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }
        }
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity deleteArtikel(@PathVariable("id") String id) {
        try {
            var artikel = artikelRestService.getArtikelByID(id);
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

}
