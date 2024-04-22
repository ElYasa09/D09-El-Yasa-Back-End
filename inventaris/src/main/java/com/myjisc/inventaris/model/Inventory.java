package com.myjisc.inventaris.model;

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
@Table(name = "inventory")
public class Inventory {

    @Id
    private UUID idItem = UUID.randomUUID();

    @NotNull
    @Column(name = "nama_item", nullable = false)
    private String namaItem;

    @NotNull
    @Column(name = "quantity_item", nullable = false)
    private Long quantityItem;

    @Column(name = "quantity_borrowed")
    private Long quantityBorrowed;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "image_item")
    private byte[] imageItem;
}
