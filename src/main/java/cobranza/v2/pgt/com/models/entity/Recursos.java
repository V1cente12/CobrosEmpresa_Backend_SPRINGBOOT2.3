
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "recurso", schema = "pgt")
public class Recursos implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idrecurso;
  
  private String nombre;
  
  private String descripcion;
  
  private String url;
  
  private String estado;
  
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  @OneToMany(mappedBy = "padre")
  @JsonIgnoreProperties({"padre","hibernateLazyInitializer","handler"})
  private List<Recursos> hijo;
  
  @ManyToOne
  @JoinColumn(name = "padre")
  @JsonIgnoreProperties({"hijo","hibernateLazyInitializer","handler"})
  private Recursos padre;
  
  @OneToMany(mappedBy = "idrecurso")
  @JsonIgnoreProperties({"idrecurso","idempresa","hibernateLazyInitializer","handler"})
  List<EmpresaMenu> idempresa;
  @OneToMany(mappedBy = "recurso")
  @JsonIgnoreProperties({"recurso","hibernateLazyInitializer","handler"})
  private List<RolRecurso> rolRecursos;
  private String icono;
  
  public Long getIdrecurso( ) { return idrecurso; }
  
  public void setIdrecurso(Long idrecurso) { this.idrecurso = idrecurso; }
  
  public String getNombre( ) { return nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getDescripcion( ) { return descripcion; }
  
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  
  public String getUrl( ) { return url; }
  
  public void setUrl(String url) { this.url = url; }
  
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
  
  public List<Recursos> getHijo( ) { return hijo; }
  
  public void setHijo(List<Recursos> hijo) { this.hijo = hijo; }
  
  public void setPadre(Recursos padre) { this.padre = padre; }
  
  public List<RolRecurso> getRolRecursos( ) { return rolRecursos; }
  
  public void setRolRecursos(List<RolRecurso> rolRecursos) { this.rolRecursos = rolRecursos; }
  
  public Recursos getPadre( ) { return padre; }
  
  public String getIcono( ) { return icono; }
  
  public void setIcono(String icono) { this.icono = icono; }
  
  /**
   * 
   */
  private static final long serialVersionUID = 7721529040179833570L;
}
