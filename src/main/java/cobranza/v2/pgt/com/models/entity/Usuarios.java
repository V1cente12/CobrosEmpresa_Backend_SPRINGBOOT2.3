
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuario", schema = "pgt")
public class Usuarios implements
                      Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idusuario;
  
  private String estado;
  
  private String login;
  
  private String clave;
  
  private Date fecha_caducidad;
  
  private String tipo;
  
  private Date fecha_alta;
  
  @PrePersist
  public void fecha_alta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  // private String roles;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  // private String roles;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "idempresa")
  @JsonIgnoreProperties({"idusuario","idcliente","documentoEmpresas","atcProfileEmpresa",
                         "atcMerchantDataRubroEmpresas","cargaMasivaDeudas","idrecurso",
                         "hibernateLazyInitializer","handler"})
  private Empresas idempresa;
  
  private String listamenus;
  
  private String listarecursos;
  
  @OneToMany(mappedBy = "idusuario", cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"idusuario","hibernateLazyInitializer","handler"})
  private List<RolUsuario> idrol;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idpersona")
  @JsonIgnoreProperties({"idusuario","idcliente","hibernateLazyInitializer","handler"})
  private Personas idpersona;
  
  public Usuarios() {}
  
  public Long getIdusuario( ) { return idusuario; }
  
  public void setIdusuario(Long idusuario) { this.idusuario = idusuario; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public String getLogin( ) { return login; }
  
  public void setLogin(String login) { this.login = login; }
  
  public String getClave( ) { return clave; }
  
  public void setClave(String clave) { this.clave = clave; }
  
  public Date getFecha_caducidad( ) { return fecha_caducidad; }
  
  public void setFecha_caducidad(Date fecha_caducidad) { this.fecha_caducidad = fecha_caducidad; }
  
  public String getTipo( ) { return tipo; }
  
  public void setTipo(String tipo) { this.tipo = tipo; }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_baja( ) { return fecha_baja; }
  
  public void setFecha_baja(Date fecha_baja) { this.fecha_baja = fecha_baja; }
  
  public String getUsuario_baja( ) { return usuario_baja; }
  
  public void setUsuario_baja(String usuario_baja) { this.usuario_baja = usuario_baja; }
  
  public Empresas getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Empresas idempresa) { this.idempresa = idempresa; }
  
  public Personas getIdpersona( ) { return idpersona; }
  
  public void setIdpersona(Personas idpersona) { this.idpersona = idpersona; }
  
  public List<RolUsuario> getIdrol( ) { return idrol; }
  
  public void setIdrol(List<RolUsuario> idrol) { this.idrol = idrol; }
  
  public String getListamenus( ) { return listamenus; }
  
  public void setListamenus(String listamenus) { this.listamenus = listamenus; }
  
  public String getListarecursos( ) { return listarecursos; }
  
  public void setListarecursos(String listarecursos) { this.listarecursos = listarecursos; }
  
  // public String getRoles( ) { return roles; }
  //
  // public void setRoles(String roles) { this.roles = roles; }
  
  private static final long serialVersionUID = 1L;
  
  // public String getRoles( ) { return roles; }
  //
  // public void setRoles(String roles) { this.roles = roles; }
  
}
