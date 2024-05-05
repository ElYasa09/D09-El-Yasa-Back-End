package com.myjisc.inventaris.model;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notif_message")
public class NotifMessage {
    @Id
    private UUID idNotif = UUID.randomUUID();

    @NotNull
    @Column(name = "id_peminjam", nullable = false)
    private UUID idPeminjam;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;
}
