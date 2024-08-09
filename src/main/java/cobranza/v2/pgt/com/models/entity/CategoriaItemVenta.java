
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "categoria_item_venta", schema = "bff")
public class CategoriaItemVenta implements
                                Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 5763053684278538710L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idcategoria;
  private String codigo;
  private String estado;
  private String imagen;
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  private Date fechaBaja;
  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;
  private Long idempresa;
  private String nombre;
  @Column(name = "usuario_alta")
  private String usuarioAlta;
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;
  
  @OneToMany(mappedBy = "categoriaItemVenta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"categoriaItemVenta","hibernateLazyInitializer","handler"})
  private List<ItemVenta> itemVentas;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idpadre")
  @JsonIgnoreProperties({"categoriaItemVentas","hibernateLazyInitializer","handler"})
  private CategoriaItemVenta categoriaItemVenta;
  
  @OneToMany(mappedBy = "categoriaItemVenta")
  @JsonIgnoreProperties({"categoriaItemVenta","hibernateLazyInitializer","handler"})
  private List<CategoriaItemVenta> categoriaItemVentas;
  
  public CategoriaItemVenta() {}
  
  public Long getIdcategoria( ) { return this.idcategoria; }
  
  public void setIdcategoria(Long idcategoria) { this.idcategoria = idcategoria; }
  
  public String getCodigo( ) { return this.codigo; }
  
  public void setCodigo(String codigo) { this.codigo = codigo; }
  
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
  
  public String getNombre( ) { return this.nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public CategoriaItemVenta getCategoriaItemVenta( ) { return this.categoriaItemVenta; }
  
  public void setCategoriaItemVenta(CategoriaItemVenta categoriaItemVenta) {
    this.categoriaItemVenta = categoriaItemVenta;
  }
  
  public List<CategoriaItemVenta> getCategoriaItemVentas( ) { return this.categoriaItemVentas; }
  
  public void setCategoriaItemVentas(List<CategoriaItemVenta> categoriaItemVentas) {
    this.categoriaItemVentas = categoriaItemVentas;
  }
  
  public CategoriaItemVenta addCategoriaItemVenta(CategoriaItemVenta categoriaItemVenta) {
    getCategoriaItemVentas( ).add(categoriaItemVenta);
    categoriaItemVenta.setCategoriaItemVenta(this);
    
    return categoriaItemVenta;
  }
  
  public CategoriaItemVenta removeCategoriaItemVenta(CategoriaItemVenta categoriaItemVenta) {
    getCategoriaItemVentas( ).remove(categoriaItemVenta);
    categoriaItemVenta.setCategoriaItemVenta(null);
    
    return categoriaItemVenta;
  }
  
  public List<ItemVenta> getItemVentas( ) { return this.itemVentas; }
  
  public void setItemVentas(List<ItemVenta> itemVentas) { this.itemVentas = itemVentas; }
  
  public ItemVenta addItemVenta(ItemVenta itemVenta) {
    getItemVentas( ).add(itemVenta);
    itemVenta.setCategoriaItemVenta(this);
    
    return itemVenta;
  }
  
  public ItemVenta removeItemVenta(ItemVenta itemVenta) {
    getItemVentas( ).remove(itemVenta);
    itemVenta.setCategoriaItemVenta(null);
    
    return itemVenta;
  }
  
  public String getImagen( ) { return imagen; }
  
  public void setImagen(String imagen) { this.imagen = imagen; }
  
}