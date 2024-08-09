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
public class DeudaDto {
    private Long iddeuda;
    private Long idfactura;
    private Date fecha_alta;
    private String usuario_alta;
    private Date fecha_modificacion;
    private String usuario_modificacion;
    private Date fecha_baja;
    private String usuario_baja;
    private String estado;
    private String observacion;
    private Long idrecibo;
    private ClienteDto cliente;
    //private Contrato idcontrato;
    private PagoDto pago;
}
