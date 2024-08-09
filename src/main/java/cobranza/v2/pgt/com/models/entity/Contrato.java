
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
@Table(name = "contrato", schema = "pgt")
public class Contrato implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idcontrato;
  
  private String codcontrato;
  
  private Date fecha_alta;
  private String estado;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_modificacion;
  
  private String usuario_modificacion;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  @Column(length = 30)
  private String servicio;
  
  @Column(length = 30)
  private String nit;
  
  @Column(name = "razon_social", length = 150)
  private String razonSocial;
  private Long idempresa;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idcliente")
  @JsonIgnoreProperties({"idcontrato","iddeuda","hibernateLazyInitializer","handler"})
  private Clientes idcliente;
  
  @OneToMany(mappedBy = "idcontrato", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"idcontrato","idcliente","hibernateLazyInitializer","handler"})
  private List<Deuda> iddeuda;
  
  public Long getIdcontrato( ) { return idcontrato; }
  
  public void setIdcontrato(Long idcontrato) { this.idcontrato = idcontrato; }
  
  public String getCodcontrato( ) { return codcontrato; }
  
  public void setCodcontrato(String codcontrato) { this.codcontrato = codcontrato; }
  
  public Clientes getIdcliente( ) { return idcliente; }
  
  public void setIdcliente(Clientes idcliente) { this.idcliente = idcliente; }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_modificacion( ) { return fecha_modificacion; }
  
  public void setFecha_modificacion(Date fecha_modificacion) {
    this.fecha_modificacion = fecha_modificacion;
  }
  
  public String getUsuario_modificacion( ) { return usuario_modificacion; }
  
  public void setUsuario_modificacion(String usuario_modificacion) {
    this.usuario_modificacion = usuario_modificacion;
  }
  
  public Date getFecha_baja( ) { return fecha_baja; }
  
  public void setFecha_baja(Date fecha_baja) { this.fecha_baja = fecha_baja; }
  
  public String getUsuario_baja( ) { return usuario_baja; }
  
  public void setUsuario_baja(String usuario_baja) { this.usuario_baja = usuario_baja; }
  
  public List<Deuda> getIddeuda( ) { return iddeuda; }
  
  public void setIddeuda(List<Deuda> iddeuda) { this.iddeuda = iddeuda; }
  
  public String getServicio( ) { return servicio; }
  
  public void setServicio(String servicio) { this.servicio = servicio; }
  
  public String getNit( ) { return nit; }
  
  public void setNit(String nit) { this.nit = nit; }
  
  public String getRazonSocial( ) { return razonSocial; }
  
  public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Long getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  /**
  		 * 
  		 */
  private static final long serialVersionUID = 6665016840545434974L;
}
