package com.myjisc.kelas.dto;

import com.myjisc.kelas.dto.request.CreateAbsensiRequestDTO;
import com.myjisc.kelas.model.Absensi;
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
public class AbsensiMapperImpl implements AbsensiMapper {

    @Override
    public Absensi createAbsensiDTOToAbsensi(CreateAbsensiRequestDTO createAbsensiReqiestDTO) {
        if ( createAbsensiReqiestDTO == null ) {
            return null;
        }

        Absensi absensi = new Absensi();

        absensi.setTanggalAbsen( createAbsensiReqiestDTO.getTanggalAbsen() );
        List<String> list = createAbsensiReqiestDTO.getKeteranganAbsen();
        if ( list != null ) {
            absensi.setKeteranganAbsen( new ArrayList<String>( list ) );
        }

        return absensi;
    }
}
