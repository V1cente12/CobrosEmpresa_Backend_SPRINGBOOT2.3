
package cobranza.v2.pgt.com.models.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the kiosko_usuario database table.
 * 
 */
@Entity
@Table(name = "kiosko_usuario", schema = "bff")
public class KioskoUsuario {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "fecha_alta")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  @Temporal(TemporalType.DATE)
  @Column(name = "fecha_fin_acceso")
  private Date fechaFinAcceso;
  @Temporal(TemporalType.DATE)
  @Column(name = "fecha_inicio_acceso")
  private Date fechaInicioAcceso;
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  private Long idempresa;
  private Long idusuario;
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idkiosko")
  private Kiosko kiosko;
  
  public KioskoUsuario() {}
  
  public Long getId( ) { return this.id; }
  
  public void setId(Long id) { this.id = id; }
  
  public Date getFechaAlta( ) { return this.fechaAlta; }
  
  public void setFechaAlta(Timestamp fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return this.fechaBaja; }
  
  public void setFechaBaja(Timestamp fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Date getFechaFinAcceso( ) { return this.fechaFinAcceso; }
  
  public void setFechaFinAcceso(Date fechaFinAcceso) { this.fechaFinAcceso = fechaFinAcceso; }
  
  public Date getFechaInicioAcceso( ) { return this.fechaInicioAcceso; }
  
  public void setFechaInicioAcceso(Date fechaInicioAcceso) { this.fechaInicioAcceso = fechaInicioAcceso; }
  
  public Date getFechaModificacion( ) { return this.fechaModificacion; }
  
  public void setFechaModificacion(Timestamp fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }
  
  public Long getIdempresa( ) { return this.idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public Long getIdusuario( ) { return this.idusuario; }
  
  public void setIdusuario(Long idusuario) { this.idusuario = idusuario; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public Kiosko getKiosko( ) { return this.kiosko; }
  
  public void setKiosko(Kiosko kiosko) { this.kiosko = kiosko; }
  
}