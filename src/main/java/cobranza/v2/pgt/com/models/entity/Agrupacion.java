
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the agrupacion database table.
 * 
 */
@Entity
@Table(name = "agrupacion", schema = "bff")
public class Agrupacion implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idagrupacion;
  
  private String estado;
  
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  
  @Temporal(TemporalType.DATE)
  @Column(name = "fin_vigencia")
  private Date finVigencia;
  
  private Integer idempresa;
  
  @Temporal(TemporalType.DATE)
  @Column(name = "inicio_vigencia")
  private Date inicioVigencia;
  
  @Column(name = "modifica_precio_por_agrupacion")
  private Boolean modificaPrecioPorAgrupacion;
  
  private String nombre;
  
  @Column(name = "tipo_agrupacion")
  private String tipoAgrupacion;
  
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  
  // bi-directional many-to-one association to AgrupacionItemVenta
  @OneToMany(mappedBy = "agrupacion")
  private List<AgrupacionItemVenta> agrupacionItemVentas;
  
  public Agrupacion() {}
  
  public Long getIdagrupacion( ) { return this.idagrupacion; }
  
  public void setIdagrupacion(Long idagrupacion) { this.idagrupacion = idagrupacion; }
  
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
  
  public Date getFinVigencia( ) { return this.finVigencia; }
  
  public void setFinVigencia(Date finVigencia) { this.finVigencia = finVigencia; }
  
  public Integer getIdempresa( ) { return this.idempresa; }
  
  public void setIdempresa(Integer idempresa) { this.idempresa = idempresa; }
  
  public Date getInicioVigencia( ) { return this.inicioVigencia; }
  
  public void setInicioVigencia(Date inicioVigencia) { this.inicioVigencia = inicioVigencia; }
  
  public Boolean getModificaPrecioPorAgrupacion( ) { return this.modificaPrecioPorAgrupacion; }
  
  public void setModificaPrecioPorAgrupacion(Boolean modificaPrecioPorAgrupacion) {
    this.modificaPrecioPorAgrupacion = modificaPrecioPorAgrupacion;
  }
  
  public String getNombre( ) { return this.nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getTipoAgrupacion( ) { return this.tipoAgrupacion; }
  
  public void setTipoAgrupacion(String tipoAgrupacion) { this.tipoAgrupacion = tipoAgrupacion; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public List<AgrupacionItemVenta> getAgrupacionItemVentas( ) { return this.agrupacionItemVentas; }
  
  public void setAgrupacionItemVentas(List<AgrupacionItemVenta> agrupacionItemVentas) {
    this.agrupacionItemVentas = agrupacionItemVentas;
  }
  
  public AgrupacionItemVenta addAgrupacionItemVenta(AgrupacionItemVenta agrupacionItemVenta) {
    getAgrupacionItemVentas( ).add(agrupacionItemVenta);
    agrupacionItemVenta.setAgrupacion(this);
    
    return agrupacionItemVenta;
  }
  
  public AgrupacionItemVenta removeAgrupacionItemVenta(AgrupacionItemVenta agrupacionItemVenta) {
    getAgrupacionItemVentas( ).remove(agrupacionItemVenta);
    agrupacionItemVenta.setAgrupacion(null);
    
    return agrupacionItemVenta;
  }
  
}