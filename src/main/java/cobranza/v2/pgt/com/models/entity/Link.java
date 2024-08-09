
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "links", schema = "pgt")
public class Link implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idlink;
  
  private String estado;
  private String link;
  private String correo;
  
  private String codigocliente;
  private BigDecimal monto;
  private String token;
  private String iddeuda;
  private Long idbitacora;
  private String telefono;
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  private Date fecha_modificacion;
  private String usuario_modificacion;
  private Date fecha_baja;
  private String usuario_baja;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idcliente")
  @JsonIgnoreProperties({"idlink","hibernateLazyInitializer","handler"})
  private Clientes idcliente;
  
  public Long getIdlink( ) { return idlink; }
  
  public void setIdlink(Long idlink) { this.idlink = idlink; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public String getLink( ) { return link; }
  
  public void setLink(String link) { this.link = link; }
  
  public String getCorreo( ) { return correo; }
  
  public void setCorreo(String correo) { this.correo = correo; }
  
  public Clientes getIdcliente( ) { return idcliente; }
  
  public void setIdcliente(Clientes idcliente) { this.idcliente = idcliente; }
  
  public BigDecimal getMonto( ) { return monto; }
  
  public void setMonto(BigDecimal monto) { this.monto = monto; }
  
  public String getToken( ) { return token; }
  
  public void setToken(String token) { this.token = token; }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public String getTelefono( ) { return telefono; }
  
  public void setTelefono(String telefono) { this.telefono = telefono; }
  
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
  
  public String getCodigocliente( ) { return codigocliente; }
  
  public void setCodigocliente(String codigocliente) { this.codigocliente = codigocliente; }
  
  public String getIddeuda( ) { return iddeuda; }
  
  public void setIddeuda(String iddeuda) { this.iddeuda = iddeuda; }
  
  public Long getIdbitacora( ) { return idbitacora; }
  
  public void setIdbitacora(Long idbitacora) { this.idbitacora = idbitacora; }
  
  /**
   * 
   */
  private static final long serialVersionUID = 1535692365479348546L;
}
