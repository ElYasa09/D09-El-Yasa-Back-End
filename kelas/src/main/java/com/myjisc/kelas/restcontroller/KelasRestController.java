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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.myjisc.kelas.dto.KelasMapper;
import com.myjisc.kelas.dto.MataPelajaranMapper;
import com.myjisc.kelas.dto.request.CreateKelasRequestDTO;
import com.myjisc.kelas.dto.request.CreateMapelRequestDTO;
import com.myjisc.kelas.dto.request.UpdateKelasRequestDTO;
import com.myjisc.kelas.dto.request.UpdateMapelRequestDTO;
import com.myjisc.kelas.model.Kelas;
import com.myjisc.kelas.model.MataPelajaran;
import com.myjisc.kelas.service.KelasRestService;
import com.myjisc.kelas.service.MataPelajaranRestService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/kelas")
public class KelasRestController {
    @Autowired
    KelasRestService kelasRestService;

    @Autowired
    KelasMapper kelasMapper;

    @Autowired
    MataPelajaranRestService mataPelajaranRestService;

    @Autowired
    MataPelajaranMapper mataPelajaranMapper;

    @PostMapping("/create")
    public ResponseEntity createKelas(@Valid @RequestBody CreateKelasRequestDTO KelasRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
        
        try {
            var kelasFromDTO = kelasMapper.createKelasDTOToKelas(KelasRequestDTO);
            var kelas = kelasRestService.createRestKelas(kelasFromDTO);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idKelas", kelas.getIdKelas());
            data.put("namaKelas", kelas.getNamaKelas());
            data.put("deskripsiKelas", kelas.getDeskripsiKelas());
            data.put("nuptkWaliKelas", kelas.getNuptkWaliKelas());
            data.put("nisnSiswa", kelas.getNisnSiswa());

            List<UUID> listUUIDMapel = new ArrayList<>();

            for (MataPelajaran mapel : kelas.getListMataPelajaran()) {
                listUUIDMapel.add(mapel.getIdMapel());
            }

            data.put("listMataPelajaran", listUUIDMapel);
            data.put("isDeleted", kelas.isDeleted());
            data.put("absensiList", kelas.getAbsensiList());

            responseBody.put("data", data);
    
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/view-all")
    public ResponseEntity viewAllKelas() {
        
        List<Kelas> listAvailableKelas = kelasRestService.retrieveRestAvailableKelas();

        if (listAvailableKelas.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        try {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
    
            List<Map<String, Object>> dataList = new ArrayList<>();
    
            for (Kelas kelas : listAvailableKelas) {
                Map<String, Object> data = new HashMap<>();
                data.put("idKelas", kelas.getIdKelas());
                data.put("namaKelas", kelas.getNamaKelas());
                data.put("deskripsiKelas", kelas.getDeskripsiKelas());
                data.put("nuptkWaliKelas", kelas.getNuptkWaliKelas());
                data.put("nisnSiswa", kelas.getNisnSiswa());
    
                List<UUID> listUUIDMapel = new ArrayList<>();
    
                for (MataPelajaran mapel : kelas.getListMataPelajaran()) {
                    listUUIDMapel.add(mapel.getIdMapel());
                }
    
                data.put("listMataPelajaran", listUUIDMapel);
                data.put("isDeleted", kelas.isDeleted());
                data.put("absensiList", kelas.getAbsensiList());
    
                dataList.add(data);
            }
    
            responseBody.put("data", dataList);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/{idKelas}")
    public ResponseEntity getDetailKelas(@PathVariable("idKelas") String idKelas) {
        try {
            var kelas = kelasRestService.getRestKelasByIdKelas(UUID.fromString(idKelas));
        
            if (kelas == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Kelas not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idKelas", kelas.getIdKelas());
            data.put("namaKelas", kelas.getNamaKelas());
            data.put("deskripsiKelas", kelas.getDeskripsiKelas());
            data.put("nuptkWaliKelas", kelas.getNuptkWaliKelas());
            data.put("nisnSiswa", kelas.getNisnSiswa());

            List<UUID> listUUIDMapel = new ArrayList<>();

            for (MataPelajaran mapel : kelas.getListMataPelajaran()) {
                listUUIDMapel.add(mapel.getIdMapel());
            }

            data.put("listMataPelajaran", listUUIDMapel);
            data.put("isDeleted", kelas.isDeleted());
            data.put("absensiList", kelas.getAbsensiList());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/siswa/{idSiswa}")
    public ResponseEntity getListSiswa(@PathVariable("idSiswa") Long idSiswa) {
        try {
            var kelas = kelasRestService.getRestKelasByIdSiswa(idSiswa);
        
            if (kelas == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Kelas not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idKelas", kelas.getIdKelas());
            data.put("namaKelas", kelas.getNamaKelas());
            data.put("deskripsiKelas", kelas.getDeskripsiKelas());
            data.put("nuptkWaliKelas", kelas.getNuptkWaliKelas());
            data.put("nisnSiswa", kelas.getNisnSiswa());

            List<UUID> listUUIDMapel = new ArrayList<>();

            for (MataPelajaran mapel : kelas.getListMataPelajaran()) {
                listUUIDMapel.add(mapel.getIdMapel());
            }

            data.put("listMataPelajaran", listUUIDMapel);
            data.put("isDeleted", kelas.isDeleted());
            data.put("absensiList", kelas.getAbsensiList());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
    
    @PutMapping("/update/{idKelas}")
    public ResponseEntity updateKelas(@PathVariable("idKelas") String idKelas, 
            @Valid @RequestBody UpdateKelasRequestDTO updateKelasRequestDTO, 
            BindingResult bindingResult) {
        
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
            }

            try {
                var kelasFromDTO = kelasMapper.updateKelasDTOToKelas(updateKelasRequestDTO);
                kelasFromDTO.setIdKelas(UUID.fromString(idKelas));
                var kelas = kelasRestService.updateRestKelas(kelasFromDTO);
                
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", "success");
    
                Map<String, Object> data = new HashMap<>();
                data.put("idKelas", kelas.getIdKelas());
                data.put("namaKelas", kelas.getNamaKelas());
                data.put("deskripsiKelas", kelas.getDeskripsiKelas());
                data.put("nuptkWaliKelas", kelas.getNuptkWaliKelas());
                data.put("nisnSiswa", kelas.getNisnSiswa());
    
                List<UUID> listUUIDMapel = new ArrayList<>();
    
                for (MataPelajaran mapel : kelas.getListMataPelajaran()) {
                    listUUIDMapel.add(mapel.getIdMapel());
                }
    
                data.put("listMataPelajaran", listUUIDMapel);
                data.put("isDeleted", kelas.isDeleted());
                data.put("absensiList", kelas.getAbsensiList());
    
                responseBody.put("data", data);
                
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } catch (Exception e) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Check your input again");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }
    }

    @DeleteMapping("/delete/{idKelas}")
    public ResponseEntity deleteKelas(@PathVariable("idKelas") String idKelas) {
        try {
            var kelas = kelasRestService.getRestKelasByIdKelas(UUID.fromString(idKelas));

            if (kelas == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Kelas not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            kelasRestService.deleteRestKelas(UUID.fromString(idKelas));

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Kelas has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping("/{idKelas}/create-mapel")
    public ResponseEntity createMapel(@PathVariable("idKelas") String idKelas, @Valid @RequestBody CreateMapelRequestDTO createMapelRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        try {
            var mapelFromDTO = mataPelajaranMapper.createMataPelajaranDTOToMataPelajaran(createMapelRequestDTO);
            mapelFromDTO.setKelas(kelasRestService.getRestKelasByIdKelas(UUID.fromString(idKelas)));
            var mapel = mataPelajaranRestService.createRestMataPelajaran(mapelFromDTO);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idMapel", mapel.getIdMapel());
            data.put("namaMapel", mapel.getNamaMapel());
            data.put("nuptkGuruMengajar", mapel.getNuptkGuruMengajar());
            data.put("idKelas", mapel.getKelas().getIdKelas());
            data.put("listKontenMapel", mapel.getListKontenMapel());

            responseBody.put("data", data);
    
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/mapel/{idMapel}")
    public ResponseEntity getDetailMapel(@PathVariable("idMapel") String idMapel) {
        try {
            var mapel = mataPelajaranRestService.getRestMataPelajaranByIdMataPelajaran(UUID.fromString(idMapel));
        
            if (mapel == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Mata Pelajaran not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idMapel", mapel.getIdMapel());
            data.put("namaMapel", mapel.getNamaMapel());
            data.put("nuptkGuruMengajar", mapel.getNuptkGuruMengajar());
            data.put("idKelas", mapel.getKelas().getIdKelas());
            data.put("listKontenMapel", mapel.getListKontenMapel());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PutMapping("/mapel/update/{idMapel}")
    public ResponseEntity updateMapel(@PathVariable("idMapel") String idMapel, @Valid @RequestBody UpdateMapelRequestDTO updateMapelRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        try {
            var mapelFromDTO = mataPelajaranMapper.updateMataPelajaranDTOToMataPelajaran(updateMapelRequestDTO);
            mapelFromDTO.setIdMapel(UUID.fromString(idMapel));
            var mapel = mataPelajaranRestService.updateRestMataPelajaran(mapelFromDTO);
            
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idMapel", mapel.getIdMapel());
            data.put("namaMapel", mapel.getNamaMapel());
            data.put("nuptkGuruMengajar", mapel.getNuptkGuruMengajar());
            data.put("idKelas", mapel.getKelas().getIdKelas());
            data.put("listKontenMapel", mapel.getListKontenMapel());

            responseBody.put("data", data);
            
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
    
    @DeleteMapping("/delete/mapel/{idMapel}")
    public ResponseEntity deleteMapel(@PathVariable("idMapel") String idMapel) {
        try {
            var mapel = mataPelajaranRestService.getRestMataPelajaranByIdMataPelajaran(UUID.fromString(idMapel));

            if (mapel == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Mata Pelajaran not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            mataPelajaranRestService.deleteRestMapel(UUID.fromString(idMapel));

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Mata Pelajaran has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
    
    
}
