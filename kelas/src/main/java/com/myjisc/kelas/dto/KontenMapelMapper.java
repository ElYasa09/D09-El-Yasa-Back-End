package com.myjisc.kelas.dto;

import org.mapstruct.Mapper;

import com.myjisc.kelas.dto.request.CreateKontenMapelRequestDTO;
import com.myjisc.kelas.model.KontenMapel;

@Mapper(componentModel = "spring")
public interface KontenMapelMapper {
    KontenMapel createKontenMapelDTOToKontenMapel (CreateKontenMapelRequestDTO createKontenMapelRequestDTO);
}
