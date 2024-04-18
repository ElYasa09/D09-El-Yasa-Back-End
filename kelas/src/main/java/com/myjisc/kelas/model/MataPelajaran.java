package com.myjisc.kelas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mata_pelajaran")
public class MataPelajaran {
    @Id
    private UUID idMapel = UUID.randomUUID();

    @NotNull
    @Column(name = "nama_mapel", nullable = false)
    private String namaMapel;

    @NotNull
    @Column(name = "nuptk_guru_mengajar", nullable = false)
    private Long nuptkGuruMengajar;

    @ManyToOne
    @JoinColumn(name = "mapel_kelas", referencedColumnName = "idKelas")
    private Kelas kelas;

    @OneToMany(mappedBy = "mataPelajaran", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<KontenMapel> listKontenMapel;
}
