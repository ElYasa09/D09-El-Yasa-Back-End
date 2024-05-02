package com.myjisc.inventaris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.NoSuchObjectException;

import com.myjisc.inventaris.dto.request.CreateInventoryRequestDTO;
import com.myjisc.inventaris.model.Inventory;
import com.myjisc.inventaris.model.InventoryRequest;
import com.myjisc.inventaris.repository.InventoryDb;
import com.myjisc.inventaris.repository.InventoryRequestDb;

import jakarta.transaction.Transactional;
import com.myjisc.inventaris.dto.request.CreateRequestPeminjamanDTO;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

@Service
@Transactional
public class InventoryRestServiceImpl implements InventoryRestService {

    @Autowired
    private InventoryDb inventoryDb;

    @Autowired
    private InventoryRequestDb inventoryRequestDb;

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
    public byte[] setRestItemImage(Inventory inventory, MultipartFile image) throws IOException {
        byte[] imageItem = imageUtilService.compressImage(image.getBytes());
        return imageItem;
    }

    @Override
    public Inventory getItemByIdItem(UUID idItem) {
        return inventoryDb.findById(idItem).orElse(null);
    }

    @Override
    public List<Inventory> retrieveAllItem() {
        return inventoryDb.findAll();
    };

    @Override
    public void deleteItem(UUID idItem) {
        var inventory = getItemByIdItem(idItem);
        if (inventory != null) {
            inventoryDb.delete(inventory);
        }
    };

    @Override
    public Inventory updateItem(Inventory inventoryFromDTO) throws NoSuchObjectException {
        var item = getItemByIdItem(inventoryFromDTO.getIdItem());
        if (item == null) {
            throw new NoSuchObjectException("Item not found");
        } else {
            item.setNamaItem(inventoryFromDTO.getNamaItem());
            item.setQuantityItem(inventoryFromDTO.getQuantityItem());
            inventoryDb.save(item);
            return item;
        }
    };

    @Override
    public Inventory updateItem(Inventory inventoryFromDTO, MultipartFile file) throws IOException {
        var item = getItemByIdItem(inventoryFromDTO.getIdItem());
        if (item == null) {
            throw new NoSuchObjectException("Item not found");
        } else {
            item.setNamaItem(inventoryFromDTO.getNamaItem());
            item.setQuantityItem(inventoryFromDTO.getQuantityItem());
            item.setImageItem(setRestItemImage(inventoryFromDTO, file));
            inventoryDb.save(item);
            return item;
        }
    };

    @Override
    public byte[] getImageItem(UUID idItem) throws NoSuchObjectException {
        var item = getItemByIdItem(idItem);

        if (item != null) {
            byte[] image = imageUtilService.decompressImage(item.getImageItem());

            if (image == null) {
                throw new NoSuchObjectException("Image Not Found");
            }

            return image;
        } else {
            throw new NoSuchObjectException("Berita Not Found");
        }
    };

    @Override
    public InventoryRequest createRequest(CreateRequestPeminjamanDTO inventoryRequestDTO) {
        InventoryRequest inventoryRequest = new InventoryRequest();
        
        inventoryRequest.setIdRequest(UUID.randomUUID());
        inventoryRequest.setRequestDate(inventoryRequestDTO.getRequestDate());
        inventoryRequest.setReturnDate(inventoryRequestDTO.getReturnDate());
        inventoryRequest.setStatus(inventoryRequestDTO.getStatus());
        inventoryRequest.setListIdItem(inventoryRequestDTO.getListIdItem());
        inventoryRequest.setListQuantityItem(inventoryRequestDTO.getListQuantityItem());
        
        inventoryRequestDb.save(inventoryRequest);
        return inventoryRequest;
    }

    // @Override
    // public InventoryRequest createRequest(Date startDateTime, Date endDateTime, List<Long> idRequest) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'createRequest'");
    // }


}

// @Override
// public InventoryRequest createRequest(CreateInventoryRequestDTO inventoryRequestDTO) {

//     InventoryRequest inventoryRequest = new InventoryRequest();
    
//     inventoryRequest.setIdRequest(UUID.randomUUID());
//     inventoryRequest.setRequestDate(new Date(System.currentTimeMillis()));
//     inventoryRequest.setReturnDate(new Date(System.currentTimeMillis()));
    
    
// }