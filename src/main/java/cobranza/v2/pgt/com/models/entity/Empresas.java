
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "empresa", schema = "pgt")
public class Empresas implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idempresa;
  private String razon_social;
  private String nombre;
  private String direccion;
  private String ciudad;
  private String nit;
  private String emision_nit;
  private String correos;
  private String telefono_fijo;
  private String telefono_movil;
  private String pagina_web;
  private Date fecha_creacion;;
  private Date fecha_alta;
  @Column(length = 2000)
  private String logo;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  private String estado;
  
  @OneToMany(mappedBy = "idempresa", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JsonIgnoreProperties({"idempresa","hibernateLazyInitializer","handler"})
  private List<Clientes> idcliente;
  
  @OneToMany(mappedBy = "idempresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"idempresa","idrol","hibernateLazyInitializer","handler"})
  private List<Usuarios> idusuario;
  
  @OneToMany(mappedBy = "idempresa")
  @JsonIgnoreProperties({"idempresa","hibernateLazyInitializer","handler"})
  private List<EmpresaMenu> idrecurso;
  
  @OneToMany(mappedBy = "idpadre")
  @JsonIgnoreProperties({"idpadre","hibernateLazyInitializer","handler"})
  private List<Empresas> hijo;
  
  @ManyToOne
  @JoinColumn(name = "idpadre")
  @JsonIgnoreProperties({"hijo","hibernateLazyInitializer","handler"})
  private Empresas idpadre;
  
  @OneToMany(mappedBy = "idempresa")
  @JsonIgnoreProperties({"idempresa","hibernateLazyInitializer","handler"})
  private List<AtcMerchantDataRubroEmpresa> atcMerchantDataRubroEmpresas;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "idagregador")
  @JsonIgnoreProperties({"empresas","hibernateLazyInitializer","handler"})
  private AtcProfileEmpresa atcProfileEmpresa;
  
  @OneToMany(mappedBy = "empresa", cascade = CascadeType.PERSIST)
  @JsonIgnoreProperties({"empresa","hibernateLazyInitializer","handler"})
  private List<DocumentoEmpresa> documentoEmpresas;
  
  @OneToMany(mappedBy = "empresa")
  @JsonIgnoreProperties({"empresas","empresa","hibernateLazyInitializer","handler"})
  private List<CargaMasivaDeuda> cargaMasivaDeudas;

  @OneToMany(mappedBy="empresa")
  @JsonIgnore
  @Getter
  private List<CuentaEmpresa> cuentasEmpresa;

  // @OneToMany(mappedBy = "empresa")
  // @JsonIgnoreProperties({"idempresa","idrol","hibernateLazyInitializer","handler"})
  // private List<Usuario> usuarios;
  
  // public Empresas(Long idempresa) { this.idempresa = idempresa; }
  
  public Long getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public String getRazon_social( ) { return razon_social; }
  
  public void setRazon_social(String razon_social) { this.razon_social = razon_social; }
  
  public String getNombre( ) { return nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getDireccion( ) { return direccion; }
  
  public void setDireccion(String direccion) { this.direccion = direccion; }
  
  public String getNit( ) { return nit; }
  
  public void setNit(String nit) { this.nit = nit; }
  
  public String getEmision_nit( ) { return emision_nit; }
  
  public void setEmision_nit(String emision_nit) { this.emision_nit = emision_nit; }
  
  public String getCorreos( ) { return correos; }
  
  public void setCorreos(String correos) { this.correos = correos; }
  
  public String getTelefono_fijo( ) { return telefono_fijo; }
  
  public void setTelefono_fijo(String telefono_fijo) { this.telefono_fijo = telefono_fijo; }
  
  public String getTelefono_movil( ) { return telefono_movil; }
  
  public void setTelefono_movil(String telefono_movil) { this.telefono_movil = telefono_movil; }
  
  public String getPagina_web( ) { return pagina_web; }
  
  public void setPagina_web(String pagina_web) { this.pagina_web = pagina_web; }
  
  public Date getFecha_creacion( ) { return fecha_creacion; }
  
  public void setFecha_creacion(Date fecha_creacion) { this.fecha_creacion = fecha_creacion; }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_baja( ) { return fecha_baja; }
  
  public void setFecha_baja(Date fecha_baja) { this.fecha_baja = fecha_baja; }
  
  public String getUsuario_baja( ) { return usuario_baja; }
  
  public void setUsuario_baja(String usuario_baja) { this.usuario_baja = usuario_baja; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  // public List<Clientes> getIdcliente( ) { return idcliente; }
  
  public void setIdcliente(List<Clientes> idcliente) { this.idcliente = idcliente; }
  
  public List<Usuarios> getIdusuario( ) { return idusuario; }
  
  public void setIdusuario(List<Usuarios> idusuario) { this.idusuario = idusuario; }
  
  public List<EmpresaMenu> getIdrecurso( ) { return idrecurso; }
  
  public void setIdrecurso(List<EmpresaMenu> idrecurso) { this.idrecurso = idrecurso; }
  
  public List<Empresas> getHijo( ) { return hijo; }
  
  public void setHijo(List<Empresas> hijo) { this.hijo = hijo; }
  
  public Empresas getIdpadre( ) { return idpadre; }
  
  public void setIdpadre(Empresas idpadre) { this.idpadre = idpadre; }
  
  public List<AtcMerchantDataRubroEmpresa> getAtcMerchantDataRubroEmpresas( ) {
    return atcMerchantDataRubroEmpresas;
  }
  
  public void setAtcMerchantDataRubroEmpresas(
                                              List<AtcMerchantDataRubroEmpresa> atcMerchantDataRubroEmpresas) {
    this.atcMerchantDataRubroEmpresas = atcMerchantDataRubroEmpresas;
  }
  
  public AtcProfileEmpresa getAtcProfileEmpresa( ) { return atcProfileEmpresa; }
  
  public void setAtcProfileEmpresa(AtcProfileEmpresa atcProfileEmpresa) {
    this.atcProfileEmpresa = atcProfileEmpresa;
  }
  
  public List<DocumentoEmpresa> getDocumentoEmpresas( ) { return documentoEmpresas; }
  
  public void setDocumentoEmpresas(List<DocumentoEmpresa> documentoEmpresas) {
    this.documentoEmpresas = documentoEmpresas;
  }
  
  public String getLogo( ) { return logo; }
  
  public void setLogo(String logo) { this.logo = logo; }
  
  // public List<CargaMasivaDeuda> getCargaMasivaDeudas( ) { return cargaMasivaDeudas; }
  
  public void setCargaMasivaDeudas(List<CargaMasivaDeuda> cargaMasivaDeudas) {
    this.cargaMasivaDeudas = cargaMasivaDeudas;
  }
  
  public String getCiudad( ) { return ciudad; }
  
  public void setCiudad(String ciudad) { this.ciudad = ciudad; }
  
  // public List<Usuario> getUsuarios( ) { return this.usuarios; }
  //
  // public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
  
  /**
  		 * 
  		 */
  private static final long serialVersionUID = 2954712487027312576L;
}
