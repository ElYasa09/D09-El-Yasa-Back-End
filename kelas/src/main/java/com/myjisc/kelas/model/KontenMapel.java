package com.myjisc.kelas.model;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKonten;

    @NotNull
    @Column(name = "judul_konten", nullable = false)
    private String judulKonten;

    @NotNull
    @Column(name = "isi_konten", nullable = false)
    private String isiKonten;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "file_konten")
    private byte[] fileKonten;

    @ManyToOne
    @JoinColumn(name = "konten_mapel", referencedColumnName = "idMapel")
    private MataPelajaran mataPelajaran;

}
