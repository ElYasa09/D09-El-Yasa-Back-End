package com.myjisc.inventaris.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateRequestPeminjamanDTO {
    private Long idPeminjam;
    private Date returnDate;
    private List<UUID> listIdItem;
    private List<Long> listQuantityItem;
    private String keperluanPeminjaman;
}
