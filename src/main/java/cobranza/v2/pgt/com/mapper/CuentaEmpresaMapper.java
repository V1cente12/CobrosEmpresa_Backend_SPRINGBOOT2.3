package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.CuentaEmpresaDto;
import cobranza.v2.pgt.com.dto.CuentaEmpresaParaInsertarDTO;
import cobranza.v2.pgt.com.models.entity.Banco;
import cobranza.v2.pgt.com.models.entity.CuentaEmpresa;
import cobranza.v2.pgt.com.models.entity.Empresas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CuentaEmpresaMapper {
    private final BancoMapper bancoMapper;

    public CuentaEmpresaMapper(BancoMapper bancoMapper) {
        this.bancoMapper = bancoMapper;
    }

    public List<CuentaEmpresaDto> asCuentaEmpresaDtoList(List<CuentaEmpresa> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CuentaEmpresaDto> list = new ArrayList<>(entityList.size());
        for ( CuentaEmpresa entity : entityList ) {
            list.add(asCuentaEmpresaDTO(entity) );
        }

        return list;
    }

    public CuentaEmpresaDto asCuentaEmpresaDTO(CuentaEmpresa entity) {
        if ( entity == null ) {
            return null;
        }
        CuentaEmpresaDto dto = CuentaEmpresaDto.builder()
                .estado(entity.getEstado())
                .idBanco(entity.getBanco().getIdBanco())
                .idCuentaEmpresa(entity.getIdCuentaEmpresa())
                .idEmpresa(entity.getEmpresa().getIdempresa())
                //.idTitular(entity.getTitular().getIdpersona())
                .banco(entity.getBanco().getNombre())
                .monedaCuenta(entity.getMonedaCuenta())
                .numeroCuenta(entity.getNumeroCuenta())
                .tipoCuenta(entity.getTipoCuenta())
                .build();
        return dto;
    }

    public CuentaEmpresa asEntity(CuentaEmpresaParaInsertarDTO dto) {
        if ( dto == null ) {
            return null;
        }
        Empresas empresa = new Empresas();
        empresa.setIdempresa(dto.getIdEmpresa());

        CuentaEmpresa entity = CuentaEmpresa.builder()
                .estado("ACT")
                .empresa(empresa)
                .titular(null)
                .monedaCuenta(dto.getMonedaCuenta())
                .numeroCuenta(dto.getNumeroCuenta())
                .tipoCuenta(dto.getTipoCuenta())
                .fechaAlta(new Date())
                .build();
        return entity;
    }
}
