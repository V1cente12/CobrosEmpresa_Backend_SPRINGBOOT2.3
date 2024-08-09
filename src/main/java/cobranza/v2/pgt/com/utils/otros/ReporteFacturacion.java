
package cobranza.v2.pgt.com.utils.otros;

import lombok.Data;

@Data
public class ReporteFacturacion {
  
  private String id;
  private String fecha;
  private String codigo_susursal;
  private String municipio_susursal;
  private String descripcion_operacion;
  private String numero_documento;
  private String razon_social;
  private String numero_factura;
  private String monto_total;
  private String codigo_estado;
  private String codigorecepcion;
  private String cuf;
  private String codigo_recepcion;
  private String nit;
  private String operacion;
  private String estado_transaccion;
  private String descripcion_respuesta;
  private String iva;
  private String xml;
  private String json;
  private String otros;
  
}
