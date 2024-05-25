package propensi.myjisc.user.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateScoreDTO {
    private List<String> tipeNilai;
    private List<Long> listNilai;
    private Long idSiswa;
    private UUID idMapel;
}
