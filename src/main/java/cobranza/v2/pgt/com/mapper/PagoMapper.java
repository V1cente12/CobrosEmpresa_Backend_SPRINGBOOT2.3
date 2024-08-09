package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.PagoDto;
import cobranza.v2.pgt.com.models.entity.Pagos;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDto asPagoDto(Pagos entity) {
        if ( entity == null ) {
            return null;
        }
        PagoDto dto = PagoDto.builder()
                .descripcion(entity.getDescripcion())
                .fecha_alta(entity.getFecha_alta())
                .fecha_baja(entity.getFecha_baja())
                .idpago(entity.getIdpago())
                .build();
        return dto;
    }
}
