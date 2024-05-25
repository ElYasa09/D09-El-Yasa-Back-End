package propensi.myjisc.user.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_siswa", "id_mapel" })
})
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idNilai;

    @NotNull
    @Column(name = "tipe", nullable = false)
    private List<String> tipeNilai;

    @NotNull
    @Column(name = "nilai", nullable = false)
    private List<Long> listNilai;

    @NotNull
    @Column(name = "id_siswa", nullable = false)
    private Long idSiswa;

    @NotNull
    @Column(name = "id_mapel", nullable = false)
    private UUID idMapel;
}
