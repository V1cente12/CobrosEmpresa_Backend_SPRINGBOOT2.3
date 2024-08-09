package cobranza.v2.pgt.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaDto {
    private Long idpersona;
    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private String apellido_marital;
    private String apellido_casado;
    private String tipo_documento;
    private String valor_documento;
    private String estado_civil;
    private Date fecha_nacimiento;
    private String lugar_nacimiento;
    private String nacionalidad;
    private String emision_documento;
    private Date fecha_aniversario;
    private String correo;
    private String profesion;
    private String domicilio;
    private String telefono;
    private Integer edad;
    private String foto;
    private Date fecha_alta;
    private String usuario_alta;
    private Date fecha_baja;
    private String usuario_baja;
    private Date fecha_modificacion;
    private String usuario_modificacion;
    private String estado;
//    private List<Usuarios> idusuario;
//    private List<Clientes> idcliente;
}
