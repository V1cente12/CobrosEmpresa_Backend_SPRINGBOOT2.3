package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.CargaMasivaDetalleDTO;
import cobranza.v2.pgt.com.dto.CargaMasivaDetalleListadoDTO;
import cobranza.v2.pgt.com.dto.CargaMasivaDetalleParaInsertarDTO;
import cobranza.v2.pgt.com.dto.CargaMasivaParaInsertarDTO;
import cobranza.v2.pgt.com.models.entity.Banco;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.entity.CargaMasivaDetalle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CargaMasivaDetalleMapper {

    public CargaMasivaDetalle asEntity(CargaMasivaDetalleDTO dto){
        if ( dto == null ) {
            return null;
        }

        Banco banco = Banco.builder()
                .idBanco(dto.getIdBanco())
                .build();
        CargaMasiva cargaMasiva = CargaMasiva.builder()
                .idCargaMasiva(dto.getIdCargaMasiva())
                .build();

        CargaMasivaDetalle entity = CargaMasivaDetalle.builder()
                .idCargaMasivaDetalle(dto.getIdCargaMasivaDetalle())
                .cargaMasiva(cargaMasiva)
                .tipoTransaccion(dto.getTipoTransaccion())
                .tipoDocumento(dto.getTipoDocumento())
                .banco(banco)
                .numeroCuenta(dto.getNumeroCuenta())
                .numeroDocumento(dto.getNumeroDocumento())
                .nombreBeneficiario(dto.getNombreBeneficiario())
                .monedaAbono(dto.getMonedaAbono())
                .monto(dto.getMonto())
                .periodoAbono(dto.getPeriodoAbono())
                .referencia(dto.getReferencia())
                .concepto(dto.getConcepto())
                .build();
        return entity;
    }

    public CargaMasivaDetalle asEntity(CargaMasivaDetalleDTO dto, CargaMasivaDetalle entity){
        if ( dto == null ) {
            return null;
        }

        Banco banco = Banco.builder()
                .idBanco(dto.getIdBanco())
                .build();
        CargaMasiva cargaMasiva = CargaMasiva.builder()
                .idCargaMasiva(dto.getIdCargaMasiva())
                .build();

        //entity.setIdCargaMasivaDetalle(dto.getIdCargaMasivaDetalle());
        entity.setCargaMasiva(cargaMasiva);
        entity.setTipoTransaccion(dto.getTipoTransaccion());
        entity.setTipoDocumento(dto.getTipoDocumento());
        entity.setBanco(banco);
        entity.setNumeroCuenta(dto.getNumeroCuenta());
        entity.setNumeroDocumento(dto.getNumeroDocumento());
        entity.setNombreBeneficiario(dto.getNombreBeneficiario());
        entity.setMonedaAbono(dto.getMonedaAbono());
        entity.setMonto(dto.getMonto());
        entity.setPeriodoAbono(dto.getPeriodoAbono());
        entity.setReferencia(dto.getReferencia());
        entity.setConcepto(dto.getConcepto());
        return entity;
    }

    public CargaMasivaDetalleDTO asDTO(CargaMasivaDetalle entity) {
        if ( entity == null ) {
            return null;
        }

        CargaMasivaDetalleDTO dto = CargaMasivaDetalleDTO.builder()
                .idCargaMasiva(entity.getIdCargaMasivaDetalle())
                .idCargaMasivaDetalle(entity.getIdCargaMasivaDetalle())
                .tipoTransaccion(entity.getTipoTransaccion())
                .tipoDocumento(entity.getTipoDocumento())
                .idBanco(entity.getBanco().getIdBanco())
                .numeroCuenta(entity.getNumeroCuenta())
                .numeroDocumento(entity.getNumeroDocumento())
                .nombreBeneficiario(entity.getNombreBeneficiario())
                .monedaAbono(entity.getMonedaAbono())
                .monto(entity.getMonto())
                .periodoAbono(entity.getPeriodoAbono())
                .referencia(entity.getReferencia())
                .concepto(entity.getConcepto())
                .build();
        return dto;
    }

    public List<CargaMasivaDetalle> asEntityList(List<CargaMasivaDetalleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CargaMasivaDetalle> list = new ArrayList<>(dtoList.size());
        for ( CargaMasivaDetalleDTO dto : dtoList ) {
            list.add( asEntity( dto ) );
        }

        return list;
    }

    public List<CargaMasivaDetalleDTO> asDTOList(List<CargaMasivaDetalle> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CargaMasivaDetalleDTO> list = new ArrayList<>(entityList.size());
        for ( CargaMasivaDetalle detalle : entityList ) {
            list.add( asDTO( detalle ) );
        }

        return list;
    }

    public List<CargaMasivaDetalle> fromInsertarDTOListToEntityList(CargaMasivaParaInsertarDTO dto) {
        if ( dto == null ) {
            return null;
        }
        List<CargaMasivaDetalle> detalles = new ArrayList<>();

        for (CargaMasivaDetalleParaInsertarDTO detalle: dto.getDetalle()) {

            Banco banco = new Banco();
            banco.setIdBanco(detalle.getIdBanco());

            CargaMasivaDetalle entity = CargaMasivaDetalle.builder()
                    .tipoDocumento(detalle.getTipoDocumento())
                    .tipoTransaccion(detalle.getTipoTransaccion())
                    .numeroDocumento(detalle.getNumeroDocumento())
                    .nombreBeneficiario(detalle.getNombreBeneficiario())
                    .numeroCuenta(detalle.getNumeroCuenta())
                    .banco(banco)
                    .periodoAbono(detalle.getPeriodoAbono())
                    .monedaAbono(detalle.getMonedaAbono())
                    .monto(detalle.getMonto())
                    .referencia(detalle.getReferencia())
                    .concepto(detalle.getConcepto())
                    .build();
            detalles.add(entity);
        }
        return detalles;
    }
}
