package com.myjisc.inventaris.dto;

import org.mapstruct.Mapper;

import com.myjisc.inventaris.dto.request.CreateRequestPeminjamanDTO;
import com.myjisc.inventaris.model.InventoryRequest;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper {
    InventoryRequest createRequestPeminjamanDTOToCreateRequestPeminjaman(
            CreateRequestPeminjamanDTO createRequestPeminjamanDTO);
}
