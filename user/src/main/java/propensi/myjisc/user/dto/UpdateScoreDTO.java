package propensi.myjisc.user.dto;

import jakarta.validation.Valid;

public class UpdateScoreDTO extends CreateScoreDTO {
    @Valid
    private Long idNilai;
}
