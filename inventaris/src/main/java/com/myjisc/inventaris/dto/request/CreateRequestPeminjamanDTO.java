package com.myjisc.inventaris.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateRequestPeminjamanDTO {
    @Id
    private UUID idRequest = UUID.randomUUID();

    @Id
    private UUID idSiswa = UUID.randomUUID();

    @NotNull
    @Column(name = "request_date", nullable = false)
    private Date requestDate;

    @NotNull
    @Column(name = "return_date", nullable = false)
    private Date returnDate;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "list_id_item", nullable = false)
    private List<UUID> listIdItem;

    @NotNull
    @Column(name = "list_quantity_item", nullable = false)
    private List<Long> listQuantityItem;
}
