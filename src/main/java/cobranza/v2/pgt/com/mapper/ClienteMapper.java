package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.ClienteDto;
import cobranza.v2.pgt.com.models.entity.Clientes;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    private final PersonaMapper personaMapper;
    private final EmpresaMapper empresaMapper;

    public ClienteMapper(PersonaMapper personaMapper, EmpresaMapper empresaMapper) {
        this.personaMapper = personaMapper;
        this.empresaMapper = empresaMapper;
    }

    public ClienteDto asClienteDto(Clientes entity) {
        if ( entity == null ) {
            return null;
        }
        ClienteDto dto = ClienteDto.builder()
                .codigo_cliente(entity.getCodigo_cliente())
                .estado(entity.getEstado())
                .empresa(empresaMapper.asEmpresaDto(entity.getIdempresa()))
                .persona(personaMapper.asPersonaDto(entity.getIdpersona()))
                .fecha_alta(entity.getFecha_alta())
                .fecha_baja(entity.getFecha_baja())
                .fecha_up_cliente(entity.getFecha_up_cliente())
                .idcliente(entity.getIdcliente())
                .build();
        return dto;
    }
}
