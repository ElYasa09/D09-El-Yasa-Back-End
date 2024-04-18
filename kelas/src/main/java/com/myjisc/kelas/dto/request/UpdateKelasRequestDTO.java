package com.myjisc.kelas.dto.request;

import java.util.UUID;

import jakarta.validation.Valid;

public class UpdateKelasRequestDTO extends CreateKelasRequestDTO {
    @Valid
    private UUID idKelas;
}
