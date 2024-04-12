package com.myjisc.kelas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateKelasRequestDTO {
    private String namaKelas;
    private String deskripsiKelas;
    private Long nuptkWaliKelas;
    private List<Long> nisnSiswa;
}
