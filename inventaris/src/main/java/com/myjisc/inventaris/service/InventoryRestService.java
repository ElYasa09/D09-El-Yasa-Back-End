package com.myjisc.inventaris.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.myjisc.inventaris.model.Inventory;

public interface InventoryRestService {
    Inventory createInventory(Inventory inventory, MultipartFile image) throws IOException;
    public byte[] setRestItemImage(Inventory inventory, MultipartFile image) throws IOException;
    Inventory getItemByIdItem(UUID idItem);
}
