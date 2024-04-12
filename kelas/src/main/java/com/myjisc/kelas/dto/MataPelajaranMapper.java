package com.myjisc.kelas.dto;

import org.mapstruct.Mapper;

import com.myjisc.kelas.dto.request.CreateMapelRequestDTO;
import com.myjisc.kelas.dto.request.UpdateMapelRequestDTO;
import com.myjisc.kelas.model.MataPelajaran;

@Mapper(componentModel = "spring")
public interface MataPelajaranMapper {
    MataPelajaran createMataPelajaranDTOToMataPelajaran (CreateMapelRequestDTO createMapelRequestDTO);
    MataPelajaran updateMataPelajaranDTOToMataPelajaran (UpdateMapelRequestDTO updateMapelRequestDTO);
}
