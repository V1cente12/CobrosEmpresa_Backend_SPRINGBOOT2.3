package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.*;
import cobranza.v2.pgt.com.models.entity.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReciboMapper {

    private final DeudaMapper deudaMapper;
    private final CyberSourceMapper cyberSourceMapper;

    public ReciboMapper(DeudaMapper deudaMapper, CyberSourceMapper cyberSourceMapper) {
        this.deudaMapper = deudaMapper;
        this.cyberSourceMapper = cyberSourceMapper;
    }

    public List<ReciboDto> asDTOList(List<Recibo> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ReciboDto> list = new ArrayList<>(entityList.size());
        for ( Recibo entity : entityList ) {
            list.add(asDTO(entity) );
        }

        return list;
    }

    public ReciboDto asDTO(Recibo entity){
        if ( entity == null ) {
            return null;
        }

        ReciboDto dto = ReciboDto.builder()
                .idrecibo( entity.getIdrecibo() )
                .idprogramacion( entity.getIdprogramacion() )
                .tipo_recibo( entity.getTipo_recibo() )
                .nro_recibo( entity.getNro_recibo() )
                .monto( entity.getMonto() )
                .fecha_vencimiento( entity.getFecha_vencimiento() )
                .estado( entity.getEstado() )
                .monto_interes( entity.getMonto_interes() )
                .monto_cargo( entity.getMonto_cargo() )
                .concepto_recibo( entity.getConcepto_recibo() )
                .descripcion_general( entity.getDescripcion_general() )
                .periodo( entity.getPeriodo() )
                .fecha_alta( entity.getFecha_alta() )
                .usuario_alta( entity.getUsuario_alta() )
                .fecha_modificacion( entity.getFecha_modificacion() )
                .usuario_modificacion( entity.getUsuario_modificacion() )
                .moneda( entity.getMoneda() )
                .codigo_pago( entity.getCodigo_pago() )
                .reference_number( entity.getReference_number() )
                .suscripcion( entity.getSuscripcion() )
                .nombre_apellido( entity.getNombre_apellido() )
                .nroTransaccion( entity.getNroTransaccion() )
                .lote( entity.getLote() )
                .montoComision(BigDecimal.ZERO)
                .montoComisionATC(BigDecimal.ZERO)
                .montoComisionPGT(BigDecimal.ZERO)
                .montoComisionBNB(BigDecimal.ZERO)
                .montoTotal(BigDecimal.ZERO)
                .deuda( deudaMapper.asDeudaDTO(entity.getIddeuda()))
                .cyberSource( cyberSourceMapper.asCyberSourceDtoList(entity.getIdcyber()))
                .build();
        return dto;
    }


}
