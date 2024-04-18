package com.myjisc.kelas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAbsensiRequestDTO {
    private Date tanggalAbsen;
    private List<String> keteranganAbsen;
}