package com.myjisc.artikel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateArtikelRequestDTO {
    private String judulArtikel;
    private String isiArtikel;
    private byte[] imageArtikel;
    private List<String> kategori;
}
