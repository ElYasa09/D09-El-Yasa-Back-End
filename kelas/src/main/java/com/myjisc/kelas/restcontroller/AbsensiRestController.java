package com.myjisc.kelas.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.myjisc.kelas.dto.AbsensiMapper;
import com.myjisc.kelas.dto.request.CreateAbsensiRequestDTO;
import com.myjisc.kelas.model.Absensi;
import com.myjisc.kelas.model.Kelas;
import com.myjisc.kelas.service.AbsensiRestService;
import com.myjisc.kelas.service.KelasRestService;

@RestController
@RequestMapping("/api/absensi")
public class AbsensiRestController {
    @Autowired
    private AbsensiRestService absensiRestService;
    
    @Autowired
    AbsensiMapper absensiMapper;

    @Autowired
    KelasRestService kelasRestService;

    @PostMapping("/create")
    public ResponseEntity createAbsensi(@RequestBody CreateAbsensiRequestDTO absensiDTO , BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        try {
            var absensiFromDTO = absensiMapper.createAbsensiDTOToAbsensi(absensiDTO);
            var absensi = absensiRestService.createRestAbsensi(absensiFromDTO);

            Kelas kelasAbsensi = kelasRestService.getRestKelasByIdKelas(absensi.getKelas().getIdKelas());
            
            if (kelasAbsensi == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Data not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idAbsen", absensi.getIdAbsen());
            data.put("tanggalAbsen", absensi.getTanggalAbsen());
            
            List<Long> listNISNSiswa = new ArrayList<>();

            for (var NISNsiswa : kelasAbsensi.getNisnSiswa()) {
                listNISNSiswa.add(NISNsiswa);
            }
            
            data.put("nisnSiswa", listNISNSiswa);
            data.put("keteranganAbsen", absensi.getKeteranganAbsen());
            data.put("kelas", absensi.getKelas());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/view-all")
    public ResponseEntity viewAllAbsensi() {
        List<Absensi> listAbsensi = absensiRestService.retrieveRestAllAbsensi();

        if (listAbsensi.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        try {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            List<Map<String, Object>> listData = new ArrayList<>();

            for (var absensi : listAbsensi) {
                Map<String, Object> data = new HashMap<>();
                data.put("idAbsen", absensi.getIdAbsen());
                data.put("tanggalAbsen", absensi.getTanggalAbsen());
                data.put("nisnSiswa", absensi.getNisnSiswa());
                data.put("keteranganAbsen", absensi.getKeteranganAbsen());
                data.put("kelas", absensi.getKelas());

                listData.add(data);
            }

            responseBody.put("data", listData);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }  
    }
}
