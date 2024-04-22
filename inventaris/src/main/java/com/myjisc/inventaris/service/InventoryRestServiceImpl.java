package com.myjisc.inventaris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myjisc.inventaris.model.Inventory;
import com.myjisc.inventaris.repository.InventoryDb;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class InventoryRestServiceImpl implements InventoryRestService{

    @Autowired
    private InventoryDb inventoryDb;
    
    @Autowired
    private ImageUtilService imageUtilService;

    @Override
    public Inventory createInventory(Inventory inventory, MultipartFile image) throws IOException {
        inventory.setImageItem(setRestItemImage(inventory, image));
        inventory.setQuantityBorrowed(Long.valueOf(0));
        inventoryDb.save(inventory);
        return inventory;
    }

    @Override
    public byte[] setRestItemImage(Inventory inventory, MultipartFile image) throws IOException{
        byte[] imageItem = imageUtilService.compressImage(image.getBytes());
        return imageItem;
    }

    @Override
    public Inventory getItemByIdItem(UUID idItem) {
        return inventoryDb.findById(idItem).orElse(null);
    }
}
