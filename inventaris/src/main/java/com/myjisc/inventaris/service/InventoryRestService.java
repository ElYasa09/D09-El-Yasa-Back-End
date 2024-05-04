package com.myjisc.inventaris.service;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

import java.rmi.NoSuchObjectException;
import org.springframework.web.multipart.MultipartFile;

import com.myjisc.inventaris.model.Inventory;
import com.myjisc.inventaris.model.InventoryRequest;
import com.myjisc.inventaris.model.NotifMessage;

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

    InventoryRequest getRequestById(UUID idRequest);

    InventoryRequest updateRequest(InventoryRequest inventoryRequest);

    List<InventoryRequest> retrieveAllRequest();

    void deleteRequest(UUID idRequest);

    Inventory saveInventory(Inventory inventory);

    void incrementQuantityBorrowed(UUID idItem, Long quantityBorrowed) throws IllegalArgumentException;

    void decrementQuantityBorrowed(UUID idItem, Long quantityBorrowed);

    void confirmedNotifMessage(UUID idPeminjam);

    void declinedNotifMessage(UUID idPeminjam);

    List<NotifMessage> retrieveAllNotifMessage();

    List<NotifMessage> retrieveAllNotifMessageByIdPeminjam(UUID idPeminjam);
}
