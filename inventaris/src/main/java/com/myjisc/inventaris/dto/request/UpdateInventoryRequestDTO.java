package com.myjisc.inventaris.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateInventoryRequestDTO extends CreateInventoryRequestDTO {
    @Valid
    private UUID idItem;
}
