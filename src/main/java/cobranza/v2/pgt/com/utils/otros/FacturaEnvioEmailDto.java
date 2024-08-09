
package cobranza.v2.pgt.com.utils.otros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Created: by DATEC
 * @author: Wendelin Cosme
 * @Date: 8/11/2021
 * @Project: datec-portal-web-sfe-back
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaEnvioEmailDto {
  
  private String xmlFactura;
  private String email;
  private Integer codigoDocumentoSector;
  private Integer tipoEmision;
  private Integer codigoModalidad;
}
