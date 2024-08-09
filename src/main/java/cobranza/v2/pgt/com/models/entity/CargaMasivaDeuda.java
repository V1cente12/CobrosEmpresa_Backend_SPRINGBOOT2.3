
package cobranza.v2.pgt.com.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the carga_masiva_deuda database table.
 * 
 */
@Entity
@Table(name = "carga_masiva_deuda", schema = "pgt")
public class CargaMasivaDeuda {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id;
  
  @Column(nullable = false, length = 200)
  private String descripcion;
  
  @Column(nullable = false, length = 100)
  private String estado;
  
  @Column(name = "fecha_fin_proceso")
  private Date fechaFinProceso;
  
  @Column(name = "fecha_inicio_proceso")
  private Date fechaInicioProceso;
  
  @Column(name = "usuario_alta", length = 100)
  private String usuarioAlta;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idempresa")
  @JsonIgnoreProperties({"cargaMasivaDeudas","hibernateLazyInitializer","handler"})
  private Empresas empresa;
  
  public CargaMasivaDeuda() {}
  
  public Long getId( ) { return id; }
  
  public void setId(Long id) { this.id = id; }
  
  public void setFechaFinProceso(Date fechaFinProceso) { this.fechaFinProceso = fechaFinProceso; }
  
  public void setFechaInicioProceso(Date fechaInicioProceso) {
    this.fechaInicioProceso = fechaInicioProceso;
  }
  
  public String getDescripcion( ) { return this.descripcion; }
  
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public Empresas getEmpresa( ) { return this.empresa; }
  
  public void setEmpresa(Empresas empresa) { this.empresa = empresa; }
  
}