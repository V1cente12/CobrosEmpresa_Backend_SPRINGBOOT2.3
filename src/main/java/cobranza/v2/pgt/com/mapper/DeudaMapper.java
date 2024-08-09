package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.DeudaDto;
import cobranza.v2.pgt.com.models.entity.Deuda;
import org.springframework.stereotype.Component;

@Component
public class DeudaMapper {
    private final PagoMapper pagoMapper;
    private final ClienteMapper clienteMapper;

    public DeudaMapper(PagoMapper pagoMapper, ClienteMapper clienteMapper) {
        this.pagoMapper = pagoMapper;
        this.clienteMapper = clienteMapper;
    }

    public DeudaDto asDeudaDTO(Deuda entity) {
        if ( entity == null ) {
            return null;
        }
        DeudaDto dto = DeudaDto.builder()
                .estado(entity.getEstado())
                .fecha_alta(entity.getFecha_alta())
                .iddeuda(entity.getIddeuda())
                .idfactura(entity.getIdfactura())
                .fecha_baja(entity.getFecha_baja())
                .observacion(entity.getObservacion())
                .pago( pagoMapper.asPagoDto(entity.getIdpago()))
                .cliente(clienteMapper.asClienteDto(entity.getIdcliente()))
                .build();
        return dto;
    }
}
