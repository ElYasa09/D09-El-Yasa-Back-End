package com.myjisc.inventaris.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.List;
import java.util.UUID;


@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateRequestPeminjamanDTO {
    private Date requestDate;
    private Date returnDate;
    private String status;
    private List<UUID> listIdItem;
    private List<Long> listQuantityItem;

}
