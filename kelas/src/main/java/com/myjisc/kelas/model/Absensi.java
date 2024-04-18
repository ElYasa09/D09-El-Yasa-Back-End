package com.myjisc.kelas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter  
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "absensi")
public class Absensi {
    
    @Id
    private UUID idAbsen = UUID.randomUUID();

    @NotNull
    @Column(name = "tanggal_absen", nullable = false)
    private Date tanggalAbsen;

    @NotNull
    @Column(name = "daftar_nisn_siswa", nullable = false)
    private List<Long> nisnSiswa;

    @NotNull
    @Column(name = "keterangan_absen", nullable = false)
    private List<String> keteranganAbsen;

    @ManyToOne
    @JoinColumn(name = "absensi_kelas", referencedColumnName = "idKelas")
    private Kelas kelas;
}