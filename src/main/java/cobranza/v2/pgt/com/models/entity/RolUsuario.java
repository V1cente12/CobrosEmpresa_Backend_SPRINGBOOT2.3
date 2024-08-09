
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "rol_usuario", schema = "pgt")
public class RolUsuario implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idrol_usr;
  
  private String estado;
  
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "idusuario")
  @JsonIgnoreProperties({"idrol","idusuario","hibernateLazyInitializer","handler"})
  private Usuarios idusuario;
  
  @ManyToOne
  @JoinColumn(name = "idrol")
  @JsonIgnoreProperties({"idusuario","hibernateLazyInitializer","handler"})
  private Roles idrol;
  
  public Long getIdrol_usr( ) { return idrol_usr; }
  
  public void setIdrol_usr(Long idrol_usr) { this.idrol_usr = idrol_usr; }
  
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
  
  public void setIdusuario(Usuarios idusuario) { this.idusuario = idusuario; }
  
  public Roles getIdrol( ) { return idrol; }
  
  public void setIdrol(Roles idrol) { this.idrol = idrol; }
  
  public Usuarios getIdusuario( ) { return idusuario; }
  
  /**
  * 
  */
  private static final long serialVersionUID = 7260392390792993820L;
  
}
