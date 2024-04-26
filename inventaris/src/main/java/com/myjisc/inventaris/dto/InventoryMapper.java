package com.myjisc.inventaris.dto;

import org.mapstruct.Mapper;

import com.myjisc.inventaris.dto.request.CreateInventoryRequestDTO;
import com.myjisc.inventaris.dto.request.UpdateInventoryRequestDTO;
import com.myjisc.inventaris.model.Inventory;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory createRestInventoryDTOToInventory(CreateInventoryRequestDTO createInventoryRequestDTO);

    Inventory updateRestInventoryDTOToInventory(UpdateInventoryRequestDTO updateInventoryRequestDTO);
}
