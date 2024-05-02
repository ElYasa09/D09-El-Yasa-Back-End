package com.myjisc.inventaris.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateInventoryRequestDTO {
    private String namaItem;
    private Long quantityItem;
}
