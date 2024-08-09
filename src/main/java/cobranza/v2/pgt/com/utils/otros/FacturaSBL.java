
package cobranza.v2.pgt.com.utils.otros;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the factura database table.
 * 
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacturaSBL {
  
  private Integer idfactura;
  private String contrato;
  private String cuf;
  private Timestamp fechaEmision;
  private BigDecimal montoTotal;
  private BigDecimal montoTotalMoneda;
  private Long nitEmisor;
  private String nombreRazonSocial;
  private Integer numeroFactura;
  private String pozo;
  private String segmento;
  private BigDecimal tipoCambio;
  private Integer codigoDocumentoSector;
  private List<DetalleFacturaSBL> detalleFacturas;
  
}