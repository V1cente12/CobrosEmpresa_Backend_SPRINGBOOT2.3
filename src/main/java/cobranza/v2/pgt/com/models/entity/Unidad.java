
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * The persistent class for the unidad database table.
 * 
 */
@Entity
@Table(name = "unidad", schema = "pgt")
public class Unidad implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long idunidad;
  private String estado;
  @Column(length = 30)
  private String codigo;
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  private Long idempresa;
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  @Column(length = 255)
  private String nombre;
  
  public Unidad() {}
  
  public Long getIdunidad( ) { return this.idunidad; }
  
  public void setIdunidad(Long idunidad) { this.idunidad = idunidad; }
  
  public String getCodigo( ) { return this.codigo; }
  
  public void setCodigo(String codigo) { this.codigo = codigo; }
  
  public Long getIdempresa( ) { return this.idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public String getNombre( ) { return this.nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public Date getFechaAlta( ) { return fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Date getFechaModificacion( ) { return fechaModificacion; }
  
  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }
  
  public String getUsuarioAlta( ) { return usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
}