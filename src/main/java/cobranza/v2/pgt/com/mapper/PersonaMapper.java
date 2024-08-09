package cobranza.v2.pgt.com.mapper;

import cobranza.v2.pgt.com.dto.PersonaDto;
import cobranza.v2.pgt.com.models.entity.Personas;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    public PersonaDto asPersonaDto(Personas entity) {
        if ( entity == null ) {
            return null;
        }
        PersonaDto dto = PersonaDto.builder()
                .nombres(entity.getNombres())
                .apellido_paterno(entity.getApellido_paterno())
                .apellido_materno(entity.getApellido_materno())
                .apellido_casado(entity.getApellido_casado())
                .apellido_marital(entity.getApellido_marital())
                .correo(entity.getCorreo())
                .domicilio(entity.getDomicilio())
                .emision_documento(entity.getEmision_documento())
                .estado(entity.getEstado())
                .estado_civil(entity.getEstado_civil())
                .fecha_alta(entity.getFecha_alta())
                .fecha_nacimiento(entity.getFecha_nacimiento())
                .idpersona(entity.getIdpersona())
                .lugar_nacimiento(entity.getLugar_nacimiento())
                .nacionalidad(entity.getNacionalidad())
                .telefono(entity.getTelefono())
                .tipo_documento(entity.getTipo_documento())
                .valor_documento(entity.getValor_documento())
                .build();
        return dto;
    }
}
