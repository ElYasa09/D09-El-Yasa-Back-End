package com.myjisc.kelas.dto;

import com.myjisc.kelas.dto.request.CreateKontenMapelRequestDTO;
import com.myjisc.kelas.model.KontenMapel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-19T02:55:30+0700",
    comments = "version: 1.5.0.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class KontenMapelMapperImpl implements KontenMapelMapper {

    @Override
    public KontenMapel createKontenMapelDTOToKontenMapel(CreateKontenMapelRequestDTO createKontenMapelRequestDTO) {
        if ( createKontenMapelRequestDTO == null ) {
            return null;
        }

        KontenMapel kontenMapel = new KontenMapel();

        kontenMapel.setJudulKonten( createKontenMapelRequestDTO.getJudulKonten() );
        kontenMapel.setIsiKonten( createKontenMapelRequestDTO.getIsiKonten() );

        return kontenMapel;
    }
}
