
package cobranza.v2.pgt.com.utils.otros;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Content {
  
  private String id;
  private String razonSocial;
  private String cufd;
  private String cuf;
  private String nit;
  private String nitContri;
  private String fecha;
  private BigDecimal monto;
  private String nroFactura;
  private String tipo;
  private String codigoDocumentoSector;
  private String codigoEstado;
  private String codigomodalidad;
  private String codigoRecepcion;
  private String codigo_sucursal;
  private String sucursal;
  private String puntoventa;
  private String operacionservicio;
  private String base64;
  private String xml;
  private String json;
  private String idcliente;
}
