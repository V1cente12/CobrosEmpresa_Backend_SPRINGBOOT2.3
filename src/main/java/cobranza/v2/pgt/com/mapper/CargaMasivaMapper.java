package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.*;
import cobranza.v2.pgt.com.models.entity.Banco;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.entity.CargaMasivaDetalle;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CargaMasivaMapper{

    public CargaMasivaDTO asDTO(CargaMasiva entity) {
        if ( entity == null ) {
            return null;
        }

        CargaMasivaDTO dto = CargaMasivaDTO.builder()
                .idCargaMasiva(entity.getIdCargaMasiva())
                .numeroCliente(entity.getNumeroCliente())
                .numeroCuenta(entity.getNumeroCuenta())
                .anioAbono(entity.getAnioAbono())
                .diaAbono(entity.getDiaAbono())
                .mesAbono(entity.getMesAbono())
                .build();
        return dto;
    }

    public CargaMasiva asEntity(CargaMasivaDTO dto){
        if ( dto == null ) {
            return null;
        }
        CargaMasiva entity = CargaMasiva.builder()
                .idCargaMasiva(dto.getIdCargaMasiva())
                .numeroCliente(dto.getNumeroCliente())
                .numeroCuenta(dto.getNumeroCuenta())
                .anioAbono(dto.getAnioAbono())
                .diaAbono(dto.getDiaAbono())
                //.estado()
                //.exportado(false)
                .mesAbono(dto.getMesAbono())
                .build();
        return entity;
    }

    public List<CargaMasiva> asEntityList(List<CargaMasivaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CargaMasiva> list = new ArrayList<CargaMasiva>( dtoList.size() );
        for ( CargaMasivaDTO cargaMasivaDTO : dtoList ) {
            list.add( asEntity( cargaMasivaDTO ) );
        }

        return list;
    }

    public List<CargaMasivaDTO> asDTOList(List<CargaMasiva> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CargaMasivaDTO> list = new ArrayList<CargaMasivaDTO>( entityList.size() );
        for ( CargaMasiva cargaMasiva : entityList ) {
            list.add( asDTO( cargaMasiva ) );
        }

        return list;
    }

    public CargaMasivaListadoDTO fromEntityToCargaMasivaListadoDTO(CargaMasiva entity){
        if ( entity == null ) {
            return null;
        }

        CargaMasivaListadoDTO dto = CargaMasivaListadoDTO.builder()
                .idCargaMasiva(entity.getIdCargaMasiva())
                .numeroCliente(entity.getNumeroCliente())
                .numeroCuenta(entity.getNumeroCuenta())
                .anioAbono(entity.getAnioAbono())
                .diaAbono(entity.getDiaAbono())
                .mesAbono(entity.getMesAbono())
                .exportado(entity.getExportado())
                .estado(entity.getEstado().getEstado())
                .fechaAlta(entity.getFechaAlta())
                .fechaBaja(entity.getFechaBaja())
                .fechaModificacion(entity.getFechaModificacion())
                .usuarioAlta(entity.getUsuarioAlta())
                .usuarioBaja(entity.getUsuarioBaja())
                .usuarioModificacion(entity.getUsuarioModificacion())
                .detalle(fromEntityListToListadoDTOList(entity.getDetalles()))
                .build();
        return dto;
    }

    public List<CargaMasivaListadoDTO> fromEntityToCargaMasivaListadoDTOList(List<CargaMasiva> cargasMasivas){
        if ( cargasMasivas == null ) {
            return null;
        }

        List<CargaMasivaListadoDTO> list = new ArrayList<CargaMasivaListadoDTO>(cargasMasivas.size());
        for ( CargaMasiva cargaMasiva : cargasMasivas ) {
            list.add( fromEntityToCargaMasivaListadoDTO( cargaMasiva ) );
        }

        return list;
    }

    protected List<CargaMasivaDetalleListadoDTO> fromEntityListToListadoDTOList(List<CargaMasivaDetalle> list) {
        if ( list == null ) {
            return null;
        }
        List<CargaMasivaDetalleListadoDTO> dto = new ArrayList<>(list.size());
        for ( CargaMasivaDetalle extractoDetalle : list ) {
            dto.add( fromEntityToListadoDTO( extractoDetalle ) );
        }

        return dto;
    }

    protected CargaMasivaDetalleListadoDTO fromEntityToListadoDTO(CargaMasivaDetalle detalle) {
        if ( detalle == null ) {
            return null;
        }
        BancoDTO banco = BancoDTO
                .builder()
                .idBanco(detalle.getBanco().getIdBanco())
                .activo(detalle.getBanco().getActivo())
                .nombre(detalle.getBanco().getNombre())
                .build();

        CargaMasivaDetalleListadoDTO dto = CargaMasivaDetalleListadoDTO.builder()
                .idCargaMasiva(detalle.getCargaMasiva().getIdCargaMasiva())
                .idCargaMasivaDetalle(detalle.getIdCargaMasivaDetalle())
                .banco(banco)
                .tipoTransaccion(detalle.getTipoTransaccion())
                .tipoDocumento(detalle.getTipoDocumento())
                .numeroDocumento(detalle.getNumeroDocumento())
                .nombreBeneficiario(detalle.getNombreBeneficiario())
                .numeroCuenta(detalle.getNumeroCuenta())
                .monedaAbono(detalle.getMonedaAbono())
                .monto(detalle.getMonto())
                .periodoAbono(detalle.getPeriodoAbono())
                .referencia(detalle.getReferencia())
                .concepto(detalle.getConcepto())
                .estado(detalle.getEstado().getEstado())
                .fechaAlta(detalle.getFechaAlta())
                .fechaModificacion(detalle.getFechaModificacion())
                .fechaBaja(detalle.getFechaBaja())
                .usuarioAlta(detalle.getUsuarioAlta())
                .usuarioBaja(detalle.getUsuarioBaja())
                .usuarioModificacion(detalle.getUsuarioModificacion())
                .build();
        return dto;
    }

    public CargaMasiva fromActualizarDTOToEntity(CargaMasivaParaActualizarDTO dto, CargaMasiva entity) {
        if ( dto == null ) {
            return null;
        }

        //entity.setIdCargaMasiva(dto.getIdCargaMasiva());
        entity.setNumeroCliente(dto.getNumeroCliente());
        entity.setNumeroCuenta(dto.getNumeroCuenta());
        entity.setAnioAbono(dto.getAnioAbono());
        entity.setMesAbono(dto.getMesAbono());
        entity.setDiaAbono(dto.getDiaAbono());
        return entity;
    }

    public CargaMasiva fromInsertarDTOToEntity(CargaMasivaParaInsertarDTO dto) {
        if ( dto == null ) {
            return null;
        }
        CargaMasiva entity = CargaMasiva.builder()
                .numeroCliente(dto.getNumeroCliente())
                .numeroCuenta(dto.getNumeroCuenta())
                .anioAbono(dto.getAnioAbono())
                .mesAbono(dto.getMesAbono())
                .diaAbono(dto.getDiaAbono())
                .exportado(false)
                .build();
        return entity;
    }

    public Page<CargaMasivaListadoDTO> fromEntityPagetoListadoPage(Page<CargaMasiva> page) {
        Page<CargaMasivaListadoDTO> dtoPage = page.map(this::fromEntityToCargaMasivaListadoDTO);
        return dtoPage;
    }

    public List<CargaMasivaParaReporteDTO> fromEntityToCargaMasivaParaReporteDTOList(List<CargaMasiva> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CargaMasivaParaReporteDTO> list = new ArrayList<>(entityList.size());
        for ( CargaMasiva entity : entityList ) {
            list.addAll(fromEntityToCargaMasivaParaReporteDTO(entity) );
        }

        return list;
    }

    public List<CargaMasivaParaReporteDTO> fromEntityToCargaMasivaParaReporteDTO(CargaMasiva entity){
        if ( entity == null ) {
            return null;
        }

        List<CargaMasivaParaReporteDTO> list = new ArrayList<>(entity.getDetalles().size());

        for (CargaMasivaDetalle detalle : entity.getDetalles()) {
            CargaMasivaParaReporteDTO dto = getCargaMasivaParaReporteDTO(entity, detalle);
            list.add(dto);
        }
        return list;
    }

    private CargaMasivaParaReporteDTO getCargaMasivaParaReporteDTO(CargaMasiva entity, CargaMasivaDetalle detalle) {
        return CargaMasivaParaReporteDTO.builder()
                        .idCargaMasiva(entity.getIdCargaMasiva())
                        .numeroCliente(entity.getNumeroCliente())
                        .numeroCuenta(entity.getNumeroCuenta())
                        .anioAbono(entity.getAnioAbono())
                        .mesAbono(entity.getMesAbono())
                        .diaAbono(entity.getDiaAbono())
                        .exportado(entity.getExportado())
                        .estado(entity.getEstado().getEstado())
                        .fechaAlta(entity.getFechaAlta())
                        .fechaModificacion(entity.getFechaModificacion())
                        .idCargaMasivaDetalle(detalle.getIdCargaMasivaDetalle())
                        .estadoDetalle(detalle.getEstado().getEstado())
                        .tipoTransaccion(detalle.getTipoTransaccion())
                        .tipoDocumento(detalle.getTipoDocumento())
                        .numeroDocumento(detalle.getNumeroDocumento())
                        .nombreBeneficiario(detalle.getNombreBeneficiario())
                        .periodoAbono(detalle.getPeriodoAbono())
                        .monedaAbono(detalle.getMonedaAbono())
                        .monto(detalle.getMonto())
                        .banco(detalle.getBanco().getNombre())
                        .numeroCuentaDestino(detalle.getNumeroCuenta())
                        .referencia(detalle.getReferencia())
                        .concepto(detalle.getConcepto())
                        .build();
    }
}