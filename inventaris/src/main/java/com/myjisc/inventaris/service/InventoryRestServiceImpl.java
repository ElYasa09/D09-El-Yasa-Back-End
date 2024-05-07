package com.myjisc.inventaris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.NoSuchObjectException;

import com.myjisc.inventaris.model.Inventory;
import com.myjisc.inventaris.model.InventoryRequest;
import com.myjisc.inventaris.model.NotifMessage;
import com.myjisc.inventaris.repository.InventoryDb;
import com.myjisc.inventaris.repository.InventoryRequestDb;
import com.myjisc.inventaris.repository.NotifMessageDb;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.ZoneId;
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

    @Autowired
    private NotifMessageDb notifMessageDb;

    @Override
    public Inventory createInventory(Inventory inventory, MultipartFile image) throws IOException {
        inventory.setImageItem(setRestItemImage(inventory, image));
        inventory.setQuantityBorrowed(Long.valueOf(0));
        inventoryDb.save(inventory);
        return inventory;
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
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
            throw new NoSuchObjectException("Item Not Found");
        }
    };

    @Override
    public InventoryRequest createRequest(InventoryRequest inventoryRequest) {
        var timeNow = LocalDateTime.now();
        inventoryRequest.setRequestDate(Date.from(timeNow.atZone(ZoneId.systemDefault()).toInstant()));
        inventoryRequest.setStatus("PENDING");
        inventoryRequestDb.save(inventoryRequest);
        return inventoryRequest;
    }

    @Override
    public List<InventoryRequest> retrieveAllRequest() {
        return inventoryRequestDb.findAll();
    }

    @Override
    public InventoryRequest getRequestById(UUID idRequest) {
        for (InventoryRequest request : retrieveAllRequest()) {
            if (request.getIdRequest().equals(idRequest)) {
                return request;
            }
        }
        return null;
    }

    @Override
    public InventoryRequest updateRequest(InventoryRequest inventoryRequest) {
        var request = getRequestById(inventoryRequest.getIdRequest());
        if (request != null) {
            request.setStatus(inventoryRequest.getStatus());
            inventoryRequestDb.save(request);
            return request;
        }
        return null;
    }

    @Override
    public void deleteRequest(UUID idRequest) {
        var request = getRequestById(idRequest);
        if (request != null) {
            inventoryRequestDb.delete(request);
        }
    }

    @Override
    public void incrementQuantityBorrowed(UUID idItem, Long quantityBorrowed) throws IllegalArgumentException {
        var item = getItemByIdItem(idItem);
        if (item != null) {
            if (item.getQuantityItem() >= quantityBorrowed) {
                item.setQuantityBorrowed(item.getQuantityBorrowed() + quantityBorrowed);
                inventoryDb.save(item);
            } else {
                throw new IllegalArgumentException("Quantity borrowed exceeds quantity item");
            }
        }
    }

    @Override
    public void decrementQuantityBorrowed(UUID idItem, Long quantityBorrowed) {
        var item = getItemByIdItem(idItem);
        if (item != null) {
            item.setQuantityBorrowed(item.getQuantityBorrowed() - quantityBorrowed);
            inventoryDb.save(item);
        }
    }

    @Override
    public void confirmedNotifMessage(Long idPeminjam, UUID idRequest) {
        NotifMessage notifMessage = new NotifMessage();
        notifMessage.setIdPeminjam(idPeminjam);
        notifMessage.setMessage("Peminjaman berhasil disetujui");
        notifMessageDb.save(notifMessage);
    }

    @Override
    public void declinedNotifMessage(Long idPeminjam, UUID idRequest) {
        NotifMessage notifMessage = new NotifMessage();
        notifMessage.setIdPeminjam(idPeminjam);
        notifMessage.setMessage("Peminjaman ditolak");
        notifMessageDb.save(notifMessage);
    }

    @Override
    public List<NotifMessage> retrieveAllNotifMessage() {
        return notifMessageDb.findAll();
    }

    @Override
    public List<NotifMessage> retrieveAllNotifMessageByIdPeminjam(Long idPeminjam) {
        return notifMessageDb.findAllByIdPeminjam(idPeminjam);
    }
}