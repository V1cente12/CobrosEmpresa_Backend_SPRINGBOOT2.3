
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "item_venta", schema = "bff")
public class ItemVenta implements
                       Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -3127671765952724757L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long iditem;
  @Column(length = 255)
  private String codigo;
  @Column(name = "codigo_aux", length = 255)
  private String codigoAux;
  @Column(precision = 19, scale = 2)
  private BigDecimal descuento;
  private String estado;
  private String imagen;
  private Integer ventas;
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  @Column(name = "fecha_vencimiento")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date fechaVencimiento;
  private Long idempresa;
  @Column(name = "maneja_stock")
  private Boolean manejaStock;
  private Boolean link;
  private String moneda;
  private String nombre;
  private BigDecimal precio;
  private BigDecimal stock;
  @Column(name = "tipo_descuento")
  private String tipoDescuento;
  private String unidad;
  @Column(precision = 2)
  private BigDecimal cuotas;
  @Column(name = "monto_cuotas", precision = 2)
  private BigDecimal montoCuotas;
  private String tipo;
  @Column(length = 3000)
  private String descripcion;
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  @OneToMany(mappedBy = "itemVenta1")
  private List<AdicionalesItemVenta> adicionalesItemVentas1;
  
  @OneToMany(mappedBy = "itemVenta2")
  private List<AdicionalesItemVenta> adicionalesItemVentas2;
  
  @OneToMany(mappedBy = "itemVenta")
  private List<AgrupacionItemVenta> agrupacionItemVentas;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idcategoria")
  @JsonIgnoreProperties({"itemVentas","hibernateLazyInitializer","handler"})
  private CategoriaItemVenta categoriaItemVenta;
  
  public ItemVenta() {}
  
  public Long getIditem( ) { return this.iditem; }
  
  public void setIditem(Long iditem) { this.iditem = iditem; }
  
  public String getCodigo( ) { return this.codigo; }
  
  public void setCodigo(String codigo) { this.codigo = codigo; }
  
  public BigDecimal getDescuento( ) { return this.descuento; }
  
  public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Date getFechaAlta( ) { return this.fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return this.fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Date getFechaModificacion( ) { return this.fechaModificacion; }
  
  public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }
  
  public Long getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public Boolean getManejaStock( ) { return this.manejaStock; }
  
  public void setManejaStock(Boolean manejaStock) { this.manejaStock = manejaStock; }
  
  public String getMoneda( ) { return this.moneda; }
  
  public void setMoneda(String moneda) { this.moneda = moneda; }
  
  public String getNombre( ) { return this.nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public BigDecimal getPrecio( ) { return this.precio; }
  
  public void setPrecio(BigDecimal precio) { this.precio = precio; }
  
  public BigDecimal getStock( ) { return this.stock; }
  
  public void setStock(BigDecimal stock) { this.stock = stock; }
  
  public String getTipoDescuento( ) { return this.tipoDescuento; }
  
  public void setTipoDescuento(String tipoDescuento) { this.tipoDescuento = tipoDescuento; }
  
  public String getUnidad( ) { return this.unidad; }
  
  public void setUnidad(String unidad) { this.unidad = unidad; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public List<AdicionalesItemVenta> getAdicionalesItemVentas1( ) { return this.adicionalesItemVentas1; }
  
  public void setAdicionalesItemVentas1(List<AdicionalesItemVenta> adicionalesItemVentas1) {
    this.adicionalesItemVentas1 = adicionalesItemVentas1;
  }
  
  public AdicionalesItemVenta addAdicionalesItemVentas1(AdicionalesItemVenta adicionalesItemVentas1) {
    getAdicionalesItemVentas1( ).add(adicionalesItemVentas1);
    adicionalesItemVentas1.setItemVenta1(this);
    
    return adicionalesItemVentas1;
  }
  
  public AdicionalesItemVenta removeAdicionalesItemVentas1(AdicionalesItemVenta adicionalesItemVentas1) {
    getAdicionalesItemVentas1( ).remove(adicionalesItemVentas1);
    adicionalesItemVentas1.setItemVenta1(null);
    
    return adicionalesItemVentas1;
  }
  
  public List<AdicionalesItemVenta> getAdicionalesItemVentas2( ) { return this.adicionalesItemVentas2; }
  
  public void setAdicionalesItemVentas2(List<AdicionalesItemVenta> adicionalesItemVentas2) {
    this.adicionalesItemVentas2 = adicionalesItemVentas2;
  }
  
  public AdicionalesItemVenta addAdicionalesItemVentas2(AdicionalesItemVenta adicionalesItemVentas2) {
    getAdicionalesItemVentas2( ).add(adicionalesItemVentas2);
    adicionalesItemVentas2.setItemVenta2(this);
    
    return adicionalesItemVentas2;
  }
  
  public AdicionalesItemVenta removeAdicionalesItemVentas2(AdicionalesItemVenta adicionalesItemVentas2) {
    getAdicionalesItemVentas2( ).remove(adicionalesItemVentas2);
    adicionalesItemVentas2.setItemVenta2(null);
    
    return adicionalesItemVentas2;
  }
  
  public List<AgrupacionItemVenta> getAgrupacionItemVentas( ) { return this.agrupacionItemVentas; }
  
  public void setAgrupacionItemVentas(List<AgrupacionItemVenta> agrupacionItemVentas) {
    this.agrupacionItemVentas = agrupacionItemVentas;
  }
  
  public AgrupacionItemVenta addAgrupacionItemVenta(AgrupacionItemVenta agrupacionItemVenta) {
    getAgrupacionItemVentas( ).add(agrupacionItemVenta);
    agrupacionItemVenta.setItemVenta(this);
    
    return agrupacionItemVenta;
  }
  
  public AgrupacionItemVenta removeAgrupacionItemVenta(AgrupacionItemVenta agrupacionItemVenta) {
    getAgrupacionItemVentas( ).remove(agrupacionItemVenta);
    agrupacionItemVenta.setItemVenta(null);
    
    return agrupacionItemVenta;
  }
  
  public CategoriaItemVenta getCategoriaItemVenta( ) { return this.categoriaItemVenta; }
  
  public void setCategoriaItemVenta(CategoriaItemVenta categoriaItemVenta) {
    this.categoriaItemVenta = categoriaItemVenta;
  }
  
  public String getImagen( ) { return imagen; }
  
  public void setImagen(String imagen) { this.imagen = imagen; }
  
  public String getTipo( ) { return tipo; }
  
  public void setTipo(String tipo) { this.tipo = tipo; }
  
  public String getDescripcion( ) { return descripcion; }
  
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  
  public String getCodigoAux( ) { return codigoAux; }
  
  public void setCodigoAux(String codigoAux) { this.codigoAux = codigoAux; }
  
  public Date getFechaVencimiento( ) { return fechaVencimiento; }
  
  public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
  
  public Boolean getLink( ) { return link; }
  
  public void setLink(Boolean link) { this.link = link; }
  
  public Integer getVentas( ) { return ventas; }
  
  public void setVentas(Integer ventas) { this.ventas = ventas; }
  
  public BigDecimal getCuotas( ) { return cuotas; }
  
  public void setCuotas(BigDecimal cuotas) { this.cuotas = cuotas; }
  
  public BigDecimal getMontoCuotas( ) { return montoCuotas; }
  
  public void setMontoCuotas(BigDecimal montoCuotas) { this.montoCuotas = montoCuotas; }
  
}