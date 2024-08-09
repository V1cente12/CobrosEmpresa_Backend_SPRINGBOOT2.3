
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cliente", schema = "pgt")
public class Clientes implements
                      Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idcliente;
  
  private Date fecha_up_cliente;
  
  private String estado;
  
  private String codigo_cliente;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_modificacion;
  
  private String usuario_modificacion;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "idempresa")
  @JsonIgnoreProperties({"idcliente","idusuario","idpadre","atcMerchantDataRubroEmpresas","atcProfileEmpresa",
                         "documentoEmpresas","hibernateLazyInitializer","handler"})
  private Empresas idempresa;
  
  @OneToMany(mappedBy = "idcliente", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JsonIgnoreProperties({"idcliente","idcontrato","hibernateLazyInitializer","handler"})
  private List<Deuda> iddeuda;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "idpersona")
  @JsonIgnoreProperties({"idcliente","idusuario","hibernateLazyInitializer","handler"})
  private Personas idpersona;
  
  @OneToMany(mappedBy = "idcliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"idcliente","hibernateLazyInitializer","handler"})
  private List<Link> idlink;
  
  @OneToMany(mappedBy = "idcliente", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JsonIgnoreProperties({"idcliente","iddeuda","hibernateLazyInitializer","handler"})
  private List<Contrato> idcontrato;
  
  public Long getIdcliente( ) { return idcliente; }
  
  public void setIdcliente(Long idcliente) { this.idcliente = idcliente; }
  
  public Date getFecha_up_cliente( ) { return fecha_up_cliente; }
  
  public void setFecha_up_cliente(Date fecha_up_cliente) { this.fecha_up_cliente = fecha_up_cliente; }
  
  public String getEstado( ) { return estado; }
  
  public List<Link> getIdlink( ) { return idlink; }
  
  public void setIdlink(List<Link> idlink) { this.idlink = idlink; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public String getCodigo_cliente( ) { return codigo_cliente; }
  
  public void setCodigo_cliente(String codigo_cliente) { this.codigo_cliente = codigo_cliente; }
  
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
  
  public Empresas getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Empresas idempresa) { this.idempresa = idempresa; }
  
  public List<Deuda> getIddeuda( ) { return iddeuda; }
  
  public void setIddeuda(List<Deuda> iddeuda) { this.iddeuda = iddeuda; }
  
  public Personas getIdpersona( ) { return idpersona; }
  
  public void setIdpersona(Personas idpersona) { this.idpersona = idpersona; }
  
  public List<Contrato> getIdcontrato( ) { return idcontrato; }
  
  public void setIdcontrato(List<Contrato> idcontrato) { this.idcontrato = idcontrato; }
  
  /**
   * 
   */
  private static final long serialVersionUID = 7509784102743761592L;
}
