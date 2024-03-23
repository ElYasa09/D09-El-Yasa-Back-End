package com.myjisc.berita.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "berita")
public class Berita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBerita;

    @NotNull
    @Column(name = "judul_berita", nullable = false)
    private String judulBerita;

    @NotNull
    @Column(name = "isi_berita", nullable = false, length = 1000)
    private String isiBerita;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "image")
    private byte[] imageBerita;

    @NotNull
    @Column(name = "kategori", nullable = false)
    private List<String> kategori;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted=false;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;


}

