package com.myjisc.inventaris.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myjisc.inventaris.dto.InventoryMapper;
import com.myjisc.inventaris.dto.request.CreateInventoryRequestDTO;
import com.myjisc.inventaris.service.InventoryRestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {

    @Autowired
    InventoryRestService inventoryRestService;

    @Autowired
    InventoryMapper inventoryMapper;

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
            data.put("listRequest", inventory.getListRequest());

            responseBody.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Something went wrong! Check your input again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

    }

}
