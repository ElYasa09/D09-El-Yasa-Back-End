package com.myjisc.inventaris.service;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

import java.rmi.NoSuchObjectException;
import org.springframework.web.multipart.MultipartFile;

import com.myjisc.inventaris.model.Inventory;

import com.myjisc.inventaris.model.InventoryRequest;

public interface InventoryRestService {
    Inventory createInventory(Inventory inventory, MultipartFile image) throws IOException;

    public byte[] setRestItemImage(Inventory inventory, MultipartFile image) throws IOException;

    Inventory getItemByIdItem(UUID idItem);

    List<Inventory> retrieveAllItem();

    void deleteItem(UUID idItem);

    Inventory updateItem(Inventory inventoryFromDTO, MultipartFile file) throws IOException;

    Inventory updateItem(Inventory inventoryFromDTO) throws NoSuchObjectException;

    byte[] getImageItem(UUID idItem) throws NoSuchObjectException;

    InventoryRequest createRequest(InventoryRequest inventoryRequest);
}
