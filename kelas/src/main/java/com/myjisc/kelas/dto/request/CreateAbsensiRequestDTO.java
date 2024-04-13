package com.myjisc.kelas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import com.myjisc.kelas.model.Kelas;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAbsensiRequestDTO {
    private Date tanggalAbsen;
    private List<Long> nisnSiswa;
    private List<String> keteranganAbsen;
    private Kelas kelas;
}
