package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.BancoDTO;
import cobranza.v2.pgt.com.models.entity.Banco;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BancoMapper {

    public Banco asEntity(BancoDTO dto){
        if ( dto == null ) {
            return null;
        }

        Banco banco = Banco.builder()
            .idBanco(dto.getIdBanco())
            .nombre(dto.getNombre())
            .activo(dto.getActivo())
            .build();

        return banco;
    }

    public BancoDTO asDTO(Banco entity){
        if ( entity == null ) {
            return null;
        }

        BancoDTO dto = BancoDTO.builder()
                .idBanco(entity.getIdBanco())
                .nombre(entity.getNombre())
                .activo(entity.getActivo())
                .build();

        return dto;
    }

    public List<Banco> asEntityList(List<BancoDTO> dtoList){
        if ( dtoList == null ) {
            return null;
        }

        List<Banco> list = new ArrayList<Banco>( dtoList.size() );
        for ( BancoDTO bancoDTO : dtoList ) {
            list.add( asEntity( bancoDTO ) );
        }

        return list;
    }

    public List<BancoDTO> asDTOList(List<Banco> entityList){
        if ( entityList == null ) {
            return null;
        }

        List<BancoDTO> list = new ArrayList<BancoDTO>( entityList.size() );
        for ( Banco banco : entityList ) {
            list.add( asDTO( banco ) );
        }

        return list;
    }
}
