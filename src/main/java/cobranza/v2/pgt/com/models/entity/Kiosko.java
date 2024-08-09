
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * The persistent class for the kiosko database table.
 * 
 */
@Entity
@Table(name = "kiosko", schema = "bff")
public class Kiosko implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idkiosko;
  
  private String ciudad;
  
  private String codigo;
  
  private String departamento;
  
  private String direccion;
  
  private String estado;
  
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  
  private BigDecimal latitud;
  
  private BigDecimal longitud;
  
  private String nombre;
  
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  
  public Kiosko() {}
  
  public Long getIdkiosko( ) { return this.idkiosko; }
  
  public void setIdkiosko(Long idkiosko) { this.idkiosko = idkiosko; }
  
  public String getCiudad( ) { return this.ciudad; }
  
  public void setCiudad(String ciudad) { this.ciudad = ciudad; }
  
  public String getCodigo( ) { return this.codigo; }
  
  public void setCodigo(String codigo) { this.codigo = codigo; }
  
  public String getDepartamento( ) { return this.departamento; }
  
  public void setDepartamento(String departamento) { this.departamento = departamento; }
  
  public String getDireccion( ) { return this.direccion; }
  
  public void setDireccion(String direccion) { this.direccion = direccion; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Date getFechaAlta( ) { return this.fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return this.fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Date getFechaModificacion( ) { return this.fechaModificacion; }
  
  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }
  
  public BigDecimal getLatitud( ) { return this.latitud; }
  
  public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }
  
  public BigDecimal getLongitud( ) { return this.longitud; }
  
  public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }
  
  public String getNombre( ) { return this.nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
}