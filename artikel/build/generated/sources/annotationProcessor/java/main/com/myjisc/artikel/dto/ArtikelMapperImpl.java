package com.myjisc.artikel.dto;

import com.myjisc.artikel.dto.request.CreateArtikelRequestDTO;
import com.myjisc.artikel.model.Artikel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-09T17:16:48+0700",
    comments = "version: 1.5.0.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class ArtikelMapperImpl implements ArtikelMapper {

    @Override
    public Artikel createArtikelRequestDTOToArtikel(CreateArtikelRequestDTO createArtikelRequestDTO) {
        if ( createArtikelRequestDTO == null ) {
            return null;
        }

        Artikel artikel = new Artikel();

        artikel.setJudulArtikel( createArtikelRequestDTO.getJudulArtikel() );
        artikel.setIsiArtikel( createArtikelRequestDTO.getIsiArtikel() );
        byte[] imageArtikel = createArtikelRequestDTO.getImageArtikel();
        if ( imageArtikel != null ) {
            artikel.setImageArtikel( Arrays.copyOf( imageArtikel, imageArtikel.length ) );
        }
        List<String> list = createArtikelRequestDTO.getKategori();
        if ( list != null ) {
            artikel.setKategori( new ArrayList<String>( list ) );
        }

        return artikel;
    }
}
