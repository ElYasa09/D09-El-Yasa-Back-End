package com.myjisc.artikel.dto;

import com.myjisc.artikel.dto.request.CreateArtikelRequestDTO;
import org.mapstruct.Mapper;

import com.myjisc.artikel.model.Artikel;

@Mapper(componentModel = "spring")
public interface ArtikelMapper {
    Artikel createArtikelRequestDTOToArtikel(CreateArtikelRequestDTO createArtikelRequestDTO);
}
