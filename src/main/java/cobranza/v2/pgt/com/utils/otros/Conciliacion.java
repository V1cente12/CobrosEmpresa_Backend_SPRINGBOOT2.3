
package cobranza.v2.pgt.com.utils.otros;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conciliacion {
  
  private String reference_number;
  private String valor_documento;
  private String tipo_documento;
  private BigDecimal nro_pedido;
  private BigDecimal monto_bs;
  private BigDecimal monto_usd;
  private String estado;
  private String fecha_venta;
  private String fecha_vencimiento;
  private String fecha_pago;
  private String hora_pago;
  private String forma_pago;
  private String grupo_empresa;
  private String empresa;
  private Integer idempresa;
  
}
