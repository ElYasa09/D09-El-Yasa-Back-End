package com.myjisc.berita.dto;

import com.myjisc.berita.dto.request.CreateBeritaRequestDTO;
import com.myjisc.berita.dto.request.UpdateBeritaRequestDTO;
import com.myjisc.berita.dto.response.ReadBeritaResponseDTO;
import com.myjisc.berita.model.Berita;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-18T09:26:51+0700",
    comments = "version: 1.5.0.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class BeritaMapperImpl implements BeritaMapper {

    @Override
    public Berita createRestBeritaDTOToBerita(CreateBeritaRequestDTO createBeritaRequestDTO) {
        if ( createBeritaRequestDTO == null ) {
            return null;
        }

        Berita berita = new Berita();

        berita.setJudulBerita( createBeritaRequestDTO.getJudulBerita() );
        berita.setIsiBerita( createBeritaRequestDTO.getIsiBerita() );
        byte[] imageBerita = createBeritaRequestDTO.getImageBerita();
        if ( imageBerita != null ) {
            berita.setImageBerita( Arrays.copyOf( imageBerita, imageBerita.length ) );
        }
        List<String> list = createBeritaRequestDTO.getKategori();
        if ( list != null ) {
            berita.setKategori( new ArrayList<String>( list ) );
        }

        return berita;
    }

    @Override
    public ReadBeritaResponseDTO beritaToReadBeritaResponseDTO(Berita berita) {
        if ( berita == null ) {
            return null;
        }

        ReadBeritaResponseDTO readBeritaResponseDTO = new ReadBeritaResponseDTO();

        readBeritaResponseDTO.setIdBerita( berita.getIdBerita() );
        readBeritaResponseDTO.setJudulBerita( berita.getJudulBerita() );
        readBeritaResponseDTO.setIsiBerita( berita.getIsiBerita() );
        List<String> list = berita.getKategori();
        if ( list != null ) {
            readBeritaResponseDTO.setKategori( new ArrayList<String>( list ) );
        }
        readBeritaResponseDTO.setDeleted( berita.isDeleted() );

        return readBeritaResponseDTO;
    }

    @Override
    public Berita updateRestBeritaDTOToBerita(UpdateBeritaRequestDTO createBeritaRequestDTO) {
        if ( createBeritaRequestDTO == null ) {
            return null;
        }

        Berita berita = new Berita();

        berita.setIdBerita( createBeritaRequestDTO.getIdBerita() );
        berita.setJudulBerita( createBeritaRequestDTO.getJudulBerita() );
        berita.setIsiBerita( createBeritaRequestDTO.getIsiBerita() );
        byte[] imageBerita = createBeritaRequestDTO.getImageBerita();
        if ( imageBerita != null ) {
            berita.setImageBerita( Arrays.copyOf( imageBerita, imageBerita.length ) );
        }
        List<String> list = createBeritaRequestDTO.getKategori();
        if ( list != null ) {
            berita.setKategori( new ArrayList<String>( list ) );
        }

        return berita;
    }
}
