package com.myjisc.kelas.dto;

import com.myjisc.kelas.dto.request.CreateMapelRequestDTO;
import com.myjisc.kelas.dto.request.UpdateMapelRequestDTO;
import com.myjisc.kelas.model.MataPelajaran;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-03T20:51:26+0700",
    comments = "version: 1.5.0.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class MataPelajaranMapperImpl implements MataPelajaranMapper {

    @Override
    public MataPelajaran createMataPelajaranDTOToMataPelajaran(CreateMapelRequestDTO createMapelRequestDTO) {
        if ( createMapelRequestDTO == null ) {
            return null;
        }

        MataPelajaran mataPelajaran = new MataPelajaran();

        mataPelajaran.setNamaMapel( createMapelRequestDTO.getNamaMapel() );
        mataPelajaran.setNuptkGuruMengajar( createMapelRequestDTO.getNuptkGuruMengajar() );

        return mataPelajaran;
    }

    @Override
    public MataPelajaran updateMataPelajaranDTOToMataPelajaran(UpdateMapelRequestDTO updateMapelRequestDTO) {
        if ( updateMapelRequestDTO == null ) {
            return null;
        }

        MataPelajaran mataPelajaran = new MataPelajaran();

        mataPelajaran.setIdMapel( updateMapelRequestDTO.getIdMapel() );
        mataPelajaran.setNamaMapel( updateMapelRequestDTO.getNamaMapel() );
        mataPelajaran.setNuptkGuruMengajar( updateMapelRequestDTO.getNuptkGuruMengajar() );

        return mataPelajaran;
    }
}
