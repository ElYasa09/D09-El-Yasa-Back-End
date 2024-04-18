package com.myjisc.kelas.dto;

import org.mapstruct.Mapper;

import com.myjisc.kelas.dto.request.CreateKelasRequestDTO;
import com.myjisc.kelas.dto.request.UpdateKelasRequestDTO;
import com.myjisc.kelas.model.Kelas;

@Mapper(componentModel = "spring")
public interface KelasMapper {
    Kelas createKelasDTOToKelas (CreateKelasRequestDTO createKelasRequestDTO);
    Kelas updateKelasDTOToKelas (UpdateKelasRequestDTO updateKelasRequestDTO);
}
