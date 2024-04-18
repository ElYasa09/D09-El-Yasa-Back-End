package com.myjisc.kelas.dto;

import com.myjisc.kelas.dto.request.CreateKelasRequestDTO;
import com.myjisc.kelas.dto.request.UpdateKelasRequestDTO;
import com.myjisc.kelas.model.Kelas;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-19T02:55:30+0700",
    comments = "version: 1.5.0.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class KelasMapperImpl implements KelasMapper {

    @Override
    public Kelas createKelasDTOToKelas(CreateKelasRequestDTO createKelasRequestDTO) {
        if ( createKelasRequestDTO == null ) {
            return null;
        }

        Kelas kelas = new Kelas();

        kelas.setNamaKelas( createKelasRequestDTO.getNamaKelas() );
        kelas.setDeskripsiKelas( createKelasRequestDTO.getDeskripsiKelas() );
        kelas.setNuptkWaliKelas( createKelasRequestDTO.getNuptkWaliKelas() );
        List<Long> list = createKelasRequestDTO.getNisnSiswa();
        if ( list != null ) {
            kelas.setNisnSiswa( new ArrayList<Long>( list ) );
        }

        return kelas;
    }

    @Override
    public Kelas updateKelasDTOToKelas(UpdateKelasRequestDTO updateKelasRequestDTO) {
        if ( updateKelasRequestDTO == null ) {
            return null;
        }

        Kelas kelas = new Kelas();

        kelas.setNamaKelas( updateKelasRequestDTO.getNamaKelas() );
        kelas.setDeskripsiKelas( updateKelasRequestDTO.getDeskripsiKelas() );
        kelas.setNuptkWaliKelas( updateKelasRequestDTO.getNuptkWaliKelas() );
        List<Long> list = updateKelasRequestDTO.getNisnSiswa();
        if ( list != null ) {
            kelas.setNisnSiswa( new ArrayList<Long>( list ) );
        }

        return kelas;
    }
}
