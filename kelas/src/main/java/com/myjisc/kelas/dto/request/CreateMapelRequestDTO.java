package com.myjisc.kelas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMapelRequestDTO {
    private String namaMapel;
    private Long nuptkGuruMengajar;
}
