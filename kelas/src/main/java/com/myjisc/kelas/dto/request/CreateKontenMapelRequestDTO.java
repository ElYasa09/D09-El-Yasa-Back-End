package com.myjisc.kelas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateKontenMapelRequestDTO {
    private String judulKonten;
    private String isiKonten;
}
