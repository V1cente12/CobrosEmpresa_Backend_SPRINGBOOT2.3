
package cobranza.v2.pgt.com.utils.otros;

import java.util.Date;

import lombok.Data;

/**
 * The persistent class for the evento_significativo database table.
 * 
 */
@Data
public class EventoSignificativo {
  
  private Long idevento;
  private Integer codigoMotivoEvento;
  private Integer codigoPuntoVenta;
  private String codigoRecepcion;
  private Integer codigoSucursal;
  private String cufdContingencia;
  private String cufdVigente;
  private String descripcion;
  private String estado;
  private String estadoEvento;
  private Date fechaAlta;
  private Date fechaBaja;
  private Date fechaFinEvento;
  private Date fechaInicioEvento;
  private String nit;
  private String tipo;
  private String usuarioAlta;
  private String usuarioBaja;
  
}