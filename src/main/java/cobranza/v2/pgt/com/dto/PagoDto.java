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
public class PagoDto {
    private Long idpago;
    private String descripcion;
    private Date fecha_alta;
    private String usuario_alta;
    private Date fecha_baja;
    private String usuario_baja;
    //private List<Deuda> iddeuda;
}
