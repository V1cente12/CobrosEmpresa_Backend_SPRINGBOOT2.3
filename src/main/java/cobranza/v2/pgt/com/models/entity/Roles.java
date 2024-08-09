
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "rol", schema = "pgt")
public class Roles implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idrol;
  
  private String nombre;
  
  private String descripcion;
  
  private String estado;
  
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  private Long idempresa;
  
  @OneToMany(mappedBy = "idrol")
  @JsonIgnoreProperties({"idrol","hibernateLazyInitializer","handler"})
  List<RolUsuario> idusuario;
  
  public Roles() {}
  
  public Long getIdrol( ) { return idrol; }
  
  public void setIdrol(Long idrol) { this.idrol = idrol; }
  
  public String getNombre( ) { return nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getDescripcion( ) { return descripcion; }
  
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_baja( ) { return fecha_baja; }
  
  public void setFecha_baja(Date fecha_baja) { this.fecha_baja = fecha_baja; }
  
  public String getUsuario_baja( ) { return usuario_baja; }
  
  public void setUsuario_baja(String usuario_baja) { this.usuario_baja = usuario_baja; }
  
  public Long getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public void setIdusuario(List<RolUsuario> idusuario) { this.idusuario = idusuario; }
  
  /**
   * 
   */
  private static final long serialVersionUID = -8825717994489001261L;
  
}
