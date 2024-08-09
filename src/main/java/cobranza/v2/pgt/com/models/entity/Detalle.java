
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "detalle", schema = "pgt")
public class Detalle implements
                     Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long iddetalle;
  private Long idfactura;
  private String item;
  private String estado;
  private String descripcion_item;
  private Integer cantidad;
  private BigDecimal precio_unitario;
  private BigDecimal sub_total;
  private BigDecimal descuento;
  private BigDecimal total;
  private Date fecha_alta;
  @Column(name = "cod_item")
  private String codItem;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  // private Date fecha_modificacion;
  // private String usuario_modificacion;
  private Date fecha_baja;
  private String usuario_baja;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
  @JoinColumn(name = "idrecibo")
  @JsonIgnoreProperties({"iddetalle","iddeuda","hibernateLazyInitializer","handler"})
  private Recibo idrecibo;
  
  public Detalle() {}
  
  public Long getIddetalle( ) { return iddetalle; }
  
  public void setIddetalle(Long iddetalle) { this.iddetalle = iddetalle; }
  
  public Long getIdfactura( ) { return idfactura; }
  
  public void setIdfactura(Long idfactura) { this.idfactura = idfactura; }
  
  public String getItem( ) { return item; }
  
  public void setItem(String item) { this.item = item; }
  
  public String getDescripcion_item( ) { return descripcion_item; }
  
  public void setDescripcion_item(String descripcion_item) { this.descripcion_item = descripcion_item; }
  
  public Integer getCantidad( ) { return cantidad; }
  
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
  
  public BigDecimal getPrecio_unitario( ) { return precio_unitario; }
  
  public void setPrecio_unitario(BigDecimal precio_unitario) {
    this.precio_unitario = precio_unitario.setScale(2, RoundingMode.HALF_DOWN);;
  }
  
  public BigDecimal getSub_total( ) { return sub_total; }
  
  public void setSub_total(BigDecimal sub_total) {
    this.sub_total = sub_total.setScale(2, RoundingMode.HALF_DOWN);;
  }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_baja( ) { return fecha_baja; }
  
  public void setFecha_baja(Date fecha_baja) { this.fecha_baja = fecha_baja; }
  
  public String getUsuario_baja( ) { return usuario_baja; }
  
  public void setUsuario_baja(String usuario_baja) { this.usuario_baja = usuario_baja; }
  
  public Recibo getIdrecibo( ) { return idrecibo; }
  
  public void setIdrecibo(Recibo idrecibo) { this.idrecibo = idrecibo; }
  
  public Detalle(String item,
                 String descripcion_item,
                 Integer cantidad,
                 BigDecimal precio_unitario,
                 BigDecimal sub_total) {
    this.item = item;
    this.descripcion_item = descripcion_item;
    this.cantidad = cantidad;
    this.precio_unitario = precio_unitario;
    this.sub_total = sub_total;
  }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public String getCodItem( ) { return codItem; }
  
  public void setCodItem(String codItem) { this.codItem = codItem; }
  
  public BigDecimal getDescuento( ) { return descuento; }
  
  public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
  
  public BigDecimal getTotal( ) { return total; }
  
  public void setTotal(BigDecimal total) { this.total = total; }
  
  /**
  		 * 
  		 */
  private static final long serialVersionUID = 6186704606478869934L;
}
