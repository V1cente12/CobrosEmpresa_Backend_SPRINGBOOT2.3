
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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the tipo_empresa database table.
 * 
 */
@Entity
@Table(name = "tipo_empresa", schema = "pgt")
public class TipoEmpresa implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 2206667586451297949L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long idtipoempresa;
  
  @Column(length = 1)
  private String estado;
  
  @Column(name = "nombre", length = 255)
  private String nombre;
  
  @Column(name = "fecha_alta", length = 255)
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "usuario_alta", length = 255)
  private String usuarioAlta;
  
  @Column(name = "fecha_baja", length = 255)
  private Date fechaBaja;
  
  @Column(name = "usuario_baja", length = 255)
  private String usuarioBaja;
  
  @OneToMany(mappedBy = "tipoEmpresa")
  @JsonIgnoreProperties({"tipoEmpresa","hibernateLazyInitializer","handler"})
  private List<Documento> documentos;
  
  public TipoEmpresa() {}
  
  public Long getIdtipoempresa( ) { return this.idtipoempresa; }
  
  public void setIdtipoempresa(Long idtipoempresa) { this.idtipoempresa = idtipoempresa; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Date getFechaAlta( ) { return fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public String getUsuarioBaja( ) { return usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getNombre( ) { return this.nombre; }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public List<Documento> getDocumentos( ) { return documentos; }
  
  public void setDocumentos(List<Documento> documentos) { this.documentos = documentos; }
  
  public Documento addDocumento(Documento documento) {
    getDocumentos( ).add(documento);
    documento.setTipoEmpresa(this);
    
    return documento;
  }
  
  public Documento removeDocumento(Documento documento) {
    getDocumentos( ).remove(documento);
    documento.setTipoEmpresa(null);
    
    return documento;
  }
  
}