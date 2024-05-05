package com.myjisc.inventaris.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory_request")
public class InventoryRequest {

    @Id
    private UUID idRequest = UUID.randomUUID();

    @NotNull
    @Column(name = "id_peminjam", nullable = false)
    private Long idPeminjam;

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

    @NotNull
    @Column(name = "keperluan_peminjaman", nullable = false)
    private String keperluanPeminjaman;
}
