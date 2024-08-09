
package cobranza.v2.pgt.com.utils.otros;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ReporteVendorShopify {
  
  private String id;
  private String descripcion_item;
  private Integer cantidad;
  private BigDecimal precio_unitario;
  private BigDecimal sub_total;
  private BigDecimal descuento;
  private BigDecimal total;
  private String fechav;
  private String item;
  private String cliente;
  private Long nro_recibo;
  private String fechap;
  private String descripcion;
  private String entrega;
}
