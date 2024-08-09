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
public class ClienteDto {
    private Long idcliente;
    private Date fecha_up_cliente;
    private String estado;
    private String codigo_cliente;
    private Date fecha_alta;
    private String usuario_alta;
    private Date fecha_modificacion;
    private String usuario_modificacion;
    private Date fecha_baja;
    private String usuario_baja;
    private EmpresaDto empresa;
    //private List<Deuda> iddeuda;
    private PersonaDto persona;
//    private List<Link> idlink;
//    private List<Contrato> idcontrato;
}
