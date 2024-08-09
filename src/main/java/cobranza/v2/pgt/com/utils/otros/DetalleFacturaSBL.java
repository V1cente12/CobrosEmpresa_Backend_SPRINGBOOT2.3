
package cobranza.v2.pgt.com.utils.otros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the detalle_factura database table.
 * 
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DetalleFacturaSBL {
  
  private Integer iddetalleFactura;
  
  private String descripcion;
  
}