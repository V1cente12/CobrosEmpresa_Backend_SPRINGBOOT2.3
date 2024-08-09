
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the parametrica
 * database table.
 * 
 */
@Entity
@Table(name = "parametrica", schema = "pgt")
public class Parametrica implements
                         Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long idparametrica;
  
  @Column(length = 60)
  private String codigo;
  
  @Column(length = 1000)
  private String descripcion;
  
  @Column(length = 100)
  private String dominio;
  
  @Column(length = 5)
  private String estado;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @Column(name = "fecha_baja")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaBaja;
  
  @Column(length = 1000)
  private String glosa;
  
  @Column(length = 100)
  private String subdominio;
  
  @Column(length = 10)
  private String tipo;
  
  @Column(name = "usuario_alta", length = 60)
  private String usuarioAlta;
  
  @Column(name = "usuario_baja", length = 60)
  private String usuarioBaja;
  
  @Column(length = 100)
  private String valor;
  
  @JsonIgnore
  @JsonIgnoreProperties({"idparametrica","idrecibo","hibernateLazyInitializer","handler"})
  @OneToMany(mappedBy = "idparametrica")
  private List<CyberSource> idcyber;
  
  public Parametrica() {}
  
  public List<CyberSource> getIdcyber( ) { return idcyber; }
  
  public void setIdcyber(List<CyberSource> idcyber) { this.idcyber = idcyber; }
  
  public Long getIdparametrica( ) { return idparametrica; }
  
  public void setIdparametrica(Long idparametrica) { this.idparametrica = idparametrica; }
  
  public String getCodigo( ) { return this.codigo; }
  
  public void setCodigo(String codigo) { this.codigo = codigo; }
  
  public String getDescripcion( ) { return this.descripcion; }
  
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  
  public String getDominio( ) { return this.dominio; }
  
  public void setDominio(String dominio) { this.dominio = dominio; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public String getGlosa( ) { return this.glosa; }
  
  public void setGlosa(String glosa) { this.glosa = glosa; }
  
  public String getSubdominio( ) { return this.subdominio; }
  
  public void setSubdominio(String subdominio) { this.subdominio = subdominio; }
  
  public String getTipo( ) { return this.tipo; }
  
  public void setTipo(String tipo) { this.tipo = tipo; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getValor( ) { return this.valor; }
  
  public void setValor(String valor) { this.valor = valor; }
  
  public Date getFechaAlta( ) { return fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  @Override
  public String toString( ) {
    return "Parametrica [idparametrica=" + idparametrica + ", codigo=" + codigo + ", descripcion="
      + descripcion + ", dominio=" + dominio + ", estado=" + estado + ", fechaAlta=" + fechaAlta
      + ", fechaBaja=" + fechaBaja + ", glosa=" + glosa + ", subdominio=" + subdominio + ", tipo=" + tipo
      + ", usuarioAlta=" + usuarioAlta + ", usuarioBaja=" + usuarioBaja + ", valor=" + valor + ", idcyber="
      + idcyber + "]";
  }
  
}