package com.myjisc.inventaris.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myjisc.inventaris.dto.InventoryMapper;
import com.myjisc.inventaris.dto.InventoryRequestMapper;
import com.myjisc.inventaris.dto.request.CreateInventoryRequestDTO;
import com.myjisc.inventaris.dto.request.UpdateInventoryRequestDTO;
import com.myjisc.inventaris.model.Inventory;
import com.myjisc.inventaris.model.InventoryRequest;
import com.myjisc.inventaris.model.NotifMessage;
import com.myjisc.inventaris.service.InventoryRestService;

import jakarta.validation.Valid;
import com.myjisc.inventaris.dto.request.CreateRequestPeminjamanDTO;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {

    @Autowired
    InventoryRestService inventoryRestService;

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    InventoryRestService inventoryRequestService;

    @Autowired
    InventoryRequestMapper inventoryRequestMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createInventory(
            @Valid @RequestBody @ModelAttribute CreateInventoryRequestDTO createInventoryRequestDTO,
            @RequestPart(value = "image", required = true) MultipartFile image, BindingResult bindingResult)
            throws IOException {

        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        try {
            var inventoryFromDTO = inventoryMapper.createRestInventoryDTOToInventory(createInventoryRequestDTO);
            var inventory = inventoryRestService.createInventory(inventoryFromDTO, image);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idItem", inventory.getIdItem());
            data.put("namaItem", inventory.getNamaItem());
            data.put("quantityItem", inventory.getQuantityItem());
            data.put("quantityBorrowed", inventory.getQuantityBorrowed());
            data.put("imageItem", "image succesfully uploaded");

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong! Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

    }

    @GetMapping("/view-all")
    public ResponseEntity<?> viewAllInventory() {

        List<Inventory> listInventory = inventoryRestService.retrieveAllItem();

        if (listInventory.isEmpty() || listInventory == null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Data not found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        try {
            List<Map<String, Object>> inventoryDataList = new ArrayList<>();
            for (Inventory inventory : listInventory) {
                Map<String, Object> inventoryData = new HashMap<>();
                inventoryData.put("idItem", inventory.getIdItem());
                inventoryData.put("namaItem", inventory.getNamaItem());
                inventoryData.put("quantityItem", inventory.getQuantityItem());
                inventoryData.put("quantityBorrowed", inventory.getQuantityBorrowed());
                inventoryData.put("imageItem", "/api/inventory/" + inventory.getIdItem() + "/image");

                inventoryDataList.add(inventoryData);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", inventoryDataList);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewInventory(@PathVariable("id") String id) {
        try {
            var item = inventoryRestService.getItemByIdItem(UUID.fromString(id));

            if (item == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Item not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();

            data.put("idItem", item.getIdItem());
            data.put("namaItem", item.getNamaItem());
            data.put("quantityItem", item.getQuantityItem());
            data.put("quantityBorrowed", item.getQuantityBorrowed());
            data.put("imageItem", "/api/inventory/" + item.getIdItem() + "/image");

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInventory(@PathVariable("id") String id,
            @Valid @RequestBody @ModelAttribute UpdateInventoryRequestDTO updateInventoryRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile file, BindingResult bindingResult)
            throws IOException {

        if (bindingResult.hasFieldErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("data", "invalid data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        try {
            var inventoryFromDTO = inventoryMapper.updateRestInventoryDTOToInventory(updateInventoryRequestDTO);
            inventoryFromDTO.setIdItem(UUID.fromString(id));
            Inventory inventory = null;
            if (file != null) {
                inventory = inventoryRestService.updateItem(inventoryFromDTO, file);
            } else {
                inventory = inventoryRestService.updateItem(inventoryFromDTO);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();
            data.put("idItem", inventory.getIdItem());
            data.put("namaItem", inventory.getNamaItem());
            data.put("quantityItem", inventory.getQuantityItem());
            data.put("quantityBorrowed", inventory.getQuantityBorrowed());
            data.put("imageItem", "/api/inventory/" + inventory.getIdItem() + "/image");

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<?> getInventoryImage(@PathVariable("id") String id) {
        try {
            byte[] image = inventoryRestService.getImageItem(UUID.fromString(id));

            if (image == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Image not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable("id") String id) {
        try {
            var item = inventoryRestService.getItemByIdItem(UUID.fromString(id));

            if (item == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Item not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            inventoryRestService.deleteItem(UUID.fromString(id));

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Item has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> createAndBorrowRequest(
            @Valid @RequestBody CreateRequestPeminjamanDTO inventoryRequestDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "fail");

            responseBody.put("message:", "Something went wrong!");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        try {
            System.out.println(inventoryRequestDTO.getListIdItem());
            System.out.println(inventoryRequestDTO.getListQuantityItem());
            var inventoryReqFromDTO = inventoryRequestMapper
                    .createRequestPeminjamanDTOToCreateRequestPeminjaman(inventoryRequestDTO);

            var inventoryRequest = inventoryRequestService.createRequest(inventoryReqFromDTO);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", inventoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            // e.printStackTrace();
            responseBody.put("message", "Something went wrong! Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/borrow/view-all")
    public ResponseEntity<?> viewAllRequest() {
        try {
            List<InventoryRequest> listRequest = inventoryRequestService.retrieveAllRequest();

            if (listRequest.isEmpty() || listRequest == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Data not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            List<Map<String, Object>> inventoryRequestDataList = new ArrayList<>();
            for (InventoryRequest inventoryRequest : listRequest) {
                Map<String, Object> inventoryRequestData = new HashMap<>();
                inventoryRequestData.put("idRequest", inventoryRequest.getIdRequest());
                inventoryRequestData.put("idPeminjam", inventoryRequest.getIdPeminjam());
                inventoryRequestData.put("requestDate", inventoryRequest.getRequestDate());
                inventoryRequestData.put("returnDate", inventoryRequest.getReturnDate());
                inventoryRequestData.put("status", inventoryRequest.getStatus());
                inventoryRequestData.put("listIdItem", inventoryRequest.getListIdItem());
                inventoryRequestData.put("listQuantityItem", inventoryRequest.getListQuantityItem());

                inventoryRequestDataList.add(inventoryRequestData);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", inventoryRequestDataList);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/borrow/{idRequest}")
    public ResponseEntity<?> viewRequest(@PathVariable("idRequest") String idRequest) {
        try {
            var request = inventoryRequestService.getRequestById(UUID.fromString(idRequest));

            if (request == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Request not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");

            Map<String, Object> data = new HashMap<>();

            data.put("idRequest", request.getIdRequest());
            data.put("idPeminjam", request.getIdPeminjam());
            data.put("requestDate", request.getRequestDate());
            data.put("returnDate", request.getReturnDate());
            data.put("status", request.getStatus());
            data.put("listIdItem", request.getListIdItem());
            data.put("listQuantityItem", request.getListQuantityItem());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    // notes: decrement
    @DeleteMapping("/borrow/delete/{idRequest}")
    public ResponseEntity<?> deleteRequest(@PathVariable("idRequest") String idRequest) {
        try {            
            var request = inventoryRequestService.getRequestById(UUID.fromString(idRequest));

            if (request == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Request not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }
            request.getListIdItem().forEach(idItem -> {
                var quantityBorrowed = request.getListQuantityItem().get(request.getListIdItem().indexOf(idItem));
                inventoryRestService.decrementQuantityBorrowed(idItem, quantityBorrowed);;
            });

            inventoryRequestService.deleteRequest(UUID.fromString(idRequest));

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Request has been deleted");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    // notes: increment dan message
    @PostMapping("/borrow/confirm/{idRequest}")
    public ResponseEntity<?> confirmRequest(@PathVariable("idRequest") String idRequest) {
        try {
            var request = inventoryRequestService.getRequestById(UUID.fromString(idRequest));

            if (request == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Request not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }
            request.getListIdItem().forEach(idItem -> {
                var quantityBorrowed = request.getListQuantityItem().get(request.getListIdItem().indexOf(idItem));
                inventoryRestService.incrementQuantityBorrowed(idItem, quantityBorrowed);
            });

            request.setStatus("CONFIRMED");
            inventoryRequestService.updateRequest(request);

            inventoryRequestService.confirmedNotifMessage(request.getIdPeminjam());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Request has been confirmed");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    // notes: message
    @PostMapping("/borrow/decline/{idRequest}")
    public ResponseEntity<?> declineRequest(@PathVariable("idRequest") String idRequest) {
        try {
            var request = inventoryRequestService.getRequestById(UUID.fromString(idRequest));

            if (request == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Request not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            request.setStatus("DECLINED");
            inventoryRequestService.updateRequest(request);

            inventoryRequestService.declinedNotifMessage(request.getIdPeminjam());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", "Request has been declined");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/notif-message/{idPeminjam}")
    public ResponseEntity<?> viewAllNotifMessage(@PathVariable("idPeminjam") String idPeminjam) {
        try {
            List<NotifMessage> listNotifMessage = inventoryRequestService
                    .retrieveAllNotifMessageByIdPeminjam(UUID.fromString(idPeminjam));

            if (listNotifMessage.isEmpty() || listNotifMessage == null) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Data not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
            }

            List<Map<String, Object>> notifMessageDataList = new ArrayList<>();
            for (NotifMessage notifMessage : listNotifMessage) {
                Map<String, Object> notifMessageData = new HashMap<>();
                notifMessageData.put("idNotif", notifMessage.getIdNotif());
                notifMessageData.put("peminjam", notifMessage.getIdPeminjam());
                notifMessageData.put("message", notifMessage.getMessage());

                notifMessageDataList.add(notifMessageData);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("data", notifMessageDataList);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Unable communicate with database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}