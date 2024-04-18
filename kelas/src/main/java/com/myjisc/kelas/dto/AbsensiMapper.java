package com.myjisc.kelas.dto;

import org.mapstruct.Mapper;

import com.myjisc.kelas.dto.request.CreateAbsensiRequestDTO;
import com.myjisc.kelas.dto.request.UpdateAbsensiRequestDTO;
import com.myjisc.kelas.model.Absensi;

@Mapper(componentModel = "spring")
public interface AbsensiMapper {
    Absensi createAbsensiDTOToAbsensi(CreateAbsensiRequestDTO createAbsensiReqiestDTO);
    Absensi updateAbsensiDTOToAbsensi(UpdateAbsensiRequestDTO updateAbsensiRequestDTO);
}
