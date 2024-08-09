
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
 * The persistent class for the categoria_empresa database table.
 * 
 */
@Entity
@Table(name = "categoria_empresa", schema = "bff")
public class CategoriaEmpresa implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idcategoria;
  
  private String estado;
  
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  
  private Integer idpadre;
  
  private String nombre;
  
  private Integer orden;
  
  @Column(name = "ruta_imagen")
  private String rutaImagen;
  
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  
  private Boolean visible;
  
  public CategoriaEmpresa() {}
  
  public Long getIdcategoria( ) { return this.idcategoria; }
  
  public void setIdcategoria(Long idcategoria) { this.idcategoria = idcategoria; }
  
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
  
  public Integer getIdpadre( ) { return this.idpadre; }
  
  public void setIdpadre(Integer idpadre) { this.idpadre = idpadre; }
  
  public String getNombre( ) { return this.nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public Integer getOrden( ) { return this.orden; }
  
  public void setOrden(Integer orden) { this.orden = orden; }
  
  public String getRutaImagen( ) { return this.rutaImagen; }
  
  public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public Boolean getVisible( ) { return this.visible; }
  
  public void setVisible(Boolean visible) { this.visible = visible; }
  
}