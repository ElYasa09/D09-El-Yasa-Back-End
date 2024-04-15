package propensi.myjisc.user.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Table;

@Data
@Builder
// @ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idNilai;

    @ElementCollection
    @CollectionTable(name = "tipe_nilai", joinColumns = @JoinColumn(name = "id_nilai"))
    @Column(name = "tipe")
    private List<String> tipeNilai;

    @ElementCollection
    @CollectionTable(name = "list_nilai", joinColumns = @JoinColumn(name = "id_nilai"))
    @Column(name = "nilai")
    private List<Long> listNilai;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    
}

