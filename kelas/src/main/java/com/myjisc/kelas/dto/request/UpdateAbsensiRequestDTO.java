package com.myjisc.kelas.dto.request;


import java.util.UUID;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAbsensiRequestDTO extends CreateAbsensiRequestDTO{
    @Valid
    private UUID idAbsen;
    
}
