
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the rol_recurso database table.
 * 
 */
@Entity
@Table(name = "rol_recurso", schema = "pgt")
public class RolRecurso implements
                        Serializable {
  
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long idrolrecurso;
  private Boolean alta;
  private Boolean baja;
  private Boolean consulta;
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  private Boolean modifica;
  @Column(nullable = false, length = 100)
  private String rol;
  @Column(name = "usuario_alta", length = 255)
  private String usuarioAlta;
  @Column(name = "usuario_baja", length = 255)
  private String usuarioBaja;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idrecurso", nullable = false)
  @JsonIgnoreProperties({"rolRecursos","idempresa","hibernateLazyInitializer","handler"})
  private Recursos recurso;
  
  public RolRecurso() {}
  
  public Long getIdrolrecurso( ) { return this.idrolrecurso; }
  
  public void setIdrolrecurso(Long idrolrecurso) { this.idrolrecurso = idrolrecurso; }
  
  public Boolean getAlta( ) { return this.alta; }
  
  public void setAlta(Boolean alta) { this.alta = alta; }
  
  public Boolean getBaja( ) { return this.baja; }
  
  public void setBaja(Boolean baja) { this.baja = baja; }
  
  public Boolean getConsulta( ) { return this.consulta; }
  
  public void setConsulta(Boolean consulta) { this.consulta = consulta; }
  
  public Date getFechaAlta( ) { return fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Boolean getModifica( ) { return this.modifica; }
  
  public void setModifica(Boolean modifica) { this.modifica = modifica; }
  
  public String getRol( ) { return this.rol; }
  
  public void setRol(String rol) { this.rol = rol; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public Recursos getRecurso( ) { return recurso; }
  
  public void setRecurso(Recursos recurso) { this.recurso = recurso; }
  
}