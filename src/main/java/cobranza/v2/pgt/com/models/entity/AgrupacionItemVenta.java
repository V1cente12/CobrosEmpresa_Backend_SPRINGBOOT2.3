
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * The persistent class for the agrupacion_item_venta database table.
 * 
 */
@Entity
@Table(name = "agrupacion_item_venta", schema = "bff")
public class AgrupacionItemVenta implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String estado;
  
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  
  @Column(name = "precio_agrupacion")
  private BigDecimal precioAgrupacion;
  
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  
  // bi-directional many-to-one association to Agrupacion
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idagrupacion")
  private Agrupacion agrupacion;
  
  // bi-directional many-to-one association to ItemVenta
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "iditem")
  private ItemVenta itemVenta;
  
  public AgrupacionItemVenta() {}
  
  public Long getId( ) { return this.id; }
  
  public void setId(Long id) { this.id = id; }
  
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
  
  public BigDecimal getPrecioAgrupacion( ) { return this.precioAgrupacion; }
  
  public void setPrecioAgrupacion(BigDecimal precioAgrupacion) {
    this.precioAgrupacion = precioAgrupacion;
  }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public Agrupacion getAgrupacion( ) { return this.agrupacion; }
  
  public void setAgrupacion(Agrupacion agrupacion) { this.agrupacion = agrupacion; }
  
  public ItemVenta getItemVenta( ) { return this.itemVenta; }
  
  public void setItemVenta(ItemVenta itemVenta) { this.itemVenta = itemVenta; }
  
}