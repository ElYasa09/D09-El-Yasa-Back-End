package com.myjisc.kelas.model;


import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "konten_mapel")
public class KontenMapel {
    @Id
    private UUID idKonten = UUID.randomUUID();

    @NotNull
    @Column(name = "judul_konten", nullable = false)
    private String judulKonten;

    @NotNull
    @Column(name = "nama_file", nullable = false)
    private String namaFile;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "file_konten")
    private byte[] fileKonten;

    @ManyToOne
    @JoinColumn(name = "konten_mapel", referencedColumnName = "idMapel")
    private MataPelajaran mataPelajaran;

}
