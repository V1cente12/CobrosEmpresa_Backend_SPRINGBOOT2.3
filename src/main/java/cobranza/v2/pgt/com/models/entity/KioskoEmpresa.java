
package cobranza.v2.pgt.com.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the kiosko_empresa database table.
 * 
 */
@Entity
@Table(name = "kiosko_empresa", schema = "bff")
public class KioskoEmpresa {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idkioskoempresa;
  @Column(name = "estado_licencia")
  private String estadoLicencia;
  @Column(name = "fecha_alta")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  @Temporal(TemporalType.DATE)
  @Column(name = "fecha_inicio_licencia")
  private Date fechaInicioLicencia;
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  @Temporal(TemporalType.DATE)
  @Column(name = "fecha_vencimiento_licencia")
  private Date fechaVencimientoLicencia;
  private Integer idempresa;
  private Integer idkiosko;
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  
  public KioskoEmpresa() {}
  
  public Long getIdkioskoempresa( ) { return this.idkioskoempresa; }
  
  public void setIdkioskoempresa(Long idkioskoempresa) { this.idkioskoempresa = idkioskoempresa; }
  
  public String getEstadoLicencia( ) { return this.estadoLicencia; }
  
  public void setEstadoLicencia(String estadoLicencia) { this.estadoLicencia = estadoLicencia; }
  
  public Date getFechaAlta( ) { return this.fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return this.fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Date getFechaInicioLicencia( ) { return this.fechaInicioLicencia; }
  
  public void setFechaInicioLicencia(Date fechaInicioLicencia) {
    this.fechaInicioLicencia = fechaInicioLicencia;
  }
  
  public Date getFechaModificacion( ) { return this.fechaModificacion; }
  
  public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }
  
  public Date getFechaVencimientoLicencia( ) { return this.fechaVencimientoLicencia; }
  
  public void setFechaVencimientoLicencia(Date fechaVencimientoLicencia) {
    this.fechaVencimientoLicencia = fechaVencimientoLicencia;
  }
  
  public Integer getIdempresa( ) { return this.idempresa; }
  
  public void setIdempresa(Integer idempresa) { this.idempresa = idempresa; }
  
  public Integer getIdkiosko( ) { return this.idkiosko; }
  
  public void setIdkiosko(Integer idkiosko) { this.idkiosko = idkiosko; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
}