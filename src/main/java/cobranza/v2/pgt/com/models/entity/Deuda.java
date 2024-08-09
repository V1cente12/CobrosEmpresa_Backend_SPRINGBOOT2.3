
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "deuda", schema = "pgt")
public class Deuda implements
                   Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long iddeuda;
  
  private Long idfactura;
  
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_modificacion;
  
  private String usuario_modificacion;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  private String estado;
  
  @Column(length = 2000)
  private String observacion;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idrecibo")
  @JsonIgnoreProperties({"iddeuda","idcontrato","hibernateLazyInitializer","handler"})
  private Recibo idrecibo;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idcliente")
  @JsonIgnoreProperties({"iddeuda","idcontrato","hibernateLazyInitializer","handler"})
  private Clientes idcliente;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idcontrato")
  @JsonIgnoreProperties({"iddeuda","idcliente","hibernateLazyInitializer","handler"})
  private Contrato idcontrato;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idpago")
  @JsonIgnoreProperties({"iddeuda","hibernateLazyInitializer","handler"})
  private Pagos idpago;
  
  public Long getIddeuda( ) { return iddeuda; }
  
  public void setIddeuda(Long iddeuda) { this.iddeuda = iddeuda; }
  
  public Long getIdfactura( ) { return idfactura; }
  
  public void setIdfactura(Long idfactura) { this.idfactura = idfactura; }
  
  public Contrato getIdcontrato( ) { return idcontrato; }
  
  public void setIdcontrato(Contrato idcontrato) { this.idcontrato = idcontrato; }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_modificacion( ) { return fecha_modificacion; }
  
  public void setFecha_modificacion(Date fecha_modificacion) { this.fecha_modificacion = fecha_modificacion; }
  
  public String getUsuario_modificacion( ) { return usuario_modificacion; }
  
  public void setUsuario_modificacion(String usuario_modificacion) {
    this.usuario_modificacion = usuario_modificacion;
  }
  
  public Date getFecha_baja( ) { return fecha_baja; }
  
  public void setFecha_baja(Date fecha_baja) { this.fecha_baja = fecha_baja; }
  
  public String getUsuario_baja( ) { return usuario_baja; }
  
  public void setUsuario_baja(String usuario_baja) { this.usuario_baja = usuario_baja; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Recibo getIdrecibo( ) { return idrecibo; }
  
  public void setIdrecibo(Recibo idrecibo) { this.idrecibo = idrecibo; }
  
  public String getObservacion( ) { return observacion; }
  
  public void setObservacion(String observacion) { this.observacion = observacion; }
  
  public Clientes getIdcliente( ) { return idcliente; }
  
  public void setIdcliente(Clientes idcliente) { this.idcliente = idcliente; }
  
  public Pagos getIdpago( ) { return idpago; }
  
  public void setIdpago(Pagos idpago) { this.idpago = idpago; }
  
  /**
  		 * 
  		 */
  private static final long serialVersionUID = 1L;
  
}
