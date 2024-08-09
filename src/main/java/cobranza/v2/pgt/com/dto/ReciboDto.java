package cobranza.v2.pgt.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReciboDto {
    private Long idrecibo;
    private Long idprogramacion;
    private String tipo_recibo;
    private Long nro_recibo;
    private BigDecimal monto;
    private Date fecha_vencimiento;
    private String estado;
    private BigDecimal monto_interes;
    private BigDecimal monto_cargo;
    private String concepto_recibo;
    private String descripcion_general;
    private String periodo;
    private Date fecha_alta;
    private String usuario_alta;
    private Date fecha_modificacion;
    private String usuario_modificacion;
    private Integer moneda;
    private String codigo_pago;
    private String reference_number;
    private String suscripcion;
    private String nombre_apellido;
    private UUID nroTransaccion;
    private DeudaDto deuda;
    //private Contrato idcontrato;
    //private List<Detalle> iddetalle;
    private List<CyberSourceDto> cyberSource;
    private String lote;
    private BigDecimal montoComision;
    private BigDecimal montoComisionPGT;
    private BigDecimal montoComisionATC;
    private BigDecimal montoComisionBNB;
    private BigDecimal montoTotal;
}
