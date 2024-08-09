package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.CyberSourceDto;
import cobranza.v2.pgt.com.models.entity.CyberSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CyberSourceMapper {

    public List<CyberSourceDto> asCyberSourceDtoList(List<CyberSource> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CyberSourceDto> list = new ArrayList<>(entityList.size());
        for ( CyberSource entity : entityList ) {
            list.add(asCyberSourceDTO(entity) );
        }

        return list;
    }

    public CyberSourceDto asCyberSourceDTO(CyberSource entity) {
        if ( entity == null ) {
            return null;
        }
        CyberSourceDto dto = CyberSourceDto.builder()
                .card_type_name(entity.getCard_type_name())
                .idcyber(entity.getIdcyber())
                .idparametrica(entity.getIdparametrica())
                .idrecibo(entity.getIdrecibo().getIdrecibo())
                .req_bill_to_address_country(entity.getReq_bill_to_address_country())
                .build();
        return dto;
    }
}
