package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.EmpresaDto;
import cobranza.v2.pgt.com.models.entity.Empresas;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {
    private final CuentaEmpresaMapper cuentaEmpresaMapper;

    public EmpresaMapper(CuentaEmpresaMapper cuentaEmpresaMapper) {
        this.cuentaEmpresaMapper = cuentaEmpresaMapper;
    }

    public EmpresaDto asEmpresaDto(Empresas entity) {
        if ( entity == null ) {
            return null;
        }
        EmpresaDto dto = EmpresaDto.builder()
                .ciudad(entity.getCiudad())
                .correos(entity.getCorreos())
                .direccion(entity.getDireccion())
                .emision_nit(entity.getEmision_nit())
                .estado(entity.getEstado())
                .fecha_alta(entity.getFecha_alta())
                .idempresa(entity.getIdempresa())
                .nit(entity.getNit())
                .nombre(entity.getNombre())
                .razon_social(entity.getRazon_social())
                .telefono_fijo(entity.getTelefono_fijo())
                .telefono_movil(entity.getTelefono_movil())
                .cuentasEmpresa(cuentaEmpresaMapper.asCuentaEmpresaDtoList(entity.getCuentasEmpresa()))
                .build();
        return dto;
    }
}
