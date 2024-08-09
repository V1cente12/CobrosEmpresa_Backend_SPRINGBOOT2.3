
package cobranza.v2.pgt.com.utils.otros;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class facturadetalle {
  
  private String codigo_servicio;
  private Integer cantidad;
  private String descripcion;
  private BigDecimal precio_unitario;
  private BigDecimal descuento;
  private BigDecimal subtotal;
  private BigDecimal ice;
  private BigDecimal ice_esp;
  private String unidad_medida;
}
