
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
 * The persistent class for the adicionales_item_venta database table.
 * 
 */
@Entity
@Table(name = "adicionales_item_venta", schema = "bff")
public class AdicionalesItemVenta implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idadicional;
  
  @Column(name = "descuento_en_adicional")
  private BigDecimal descuentoEnAdicional;
  
  private String estado;
  
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
  
  // bi-directional many-to-one association to ItemVenta
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "iditem")
  private ItemVenta itemVenta1;
  
  // bi-directional many-to-one association to ItemVenta
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "iditem_adicional")
  private ItemVenta itemVenta2;
  
  public AdicionalesItemVenta() {}
  
  public Long getIdadicional( ) { return this.idadicional; }
  
  public void setIdadicional(Long idadicional) { this.idadicional = idadicional; }
  
  public BigDecimal getDescuentoEnAdicional( ) { return this.descuentoEnAdicional; }
  
  public void setDescuentoEnAdicional(BigDecimal descuentoEnAdicional) {
    this.descuentoEnAdicional = descuentoEnAdicional;
  }
  
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
  
  public Long getIdempresa( ) { return this.idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public ItemVenta getItemVenta1( ) { return this.itemVenta1; }
  
  public void setItemVenta1(ItemVenta itemVenta1) { this.itemVenta1 = itemVenta1; }
  
  public ItemVenta getItemVenta2( ) { return this.itemVenta2; }
  
  public void setItemVenta2(ItemVenta itemVenta2) { this.itemVenta2 = itemVenta2; }
  
}