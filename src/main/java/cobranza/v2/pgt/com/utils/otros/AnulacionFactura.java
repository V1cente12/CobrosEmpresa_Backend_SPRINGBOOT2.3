
package cobranza.v2.pgt.com.utils.otros;

import lombok.Data;

@Data
public class AnulacionFactura {
  
  public String nit;
  public String codigoSucursal;
  public String codigoPuntoVenta;
  public String codigoOperacion;
  public String codigoDocumentoSector;
  public String codigoEmision;
  public String tipoFacturaDocumento;
  public String codigoMotivo;
  public String cuf;
  public String token;
}
