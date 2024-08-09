
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the documento database table.
 * 
 */
@Entity
@Table(name = "documento", schema = "pgt")
public class Documento implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long iddocumento;
  
  @Column(nullable = false, length = 10)
  private String estado;
  
  @Column(name = "alias", length = 255)
  private String alias;
  
  @Column(name = "fecha_alta")
  private Timestamp fechaAlta;
  
  @Column(name = "fecha_baja")
  private Timestamp fechaBaja;
  
  @Column(name = "fecha_modificacion")
  private Timestamp fechaModificacion;
  
  @Column(name = "nombre_documento", nullable = false, length = 150)
  private String nombreDocumento;
  
  @Column(nullable = false, length = 10)
  private String obligatorio;
  
  @Column(name = "usuario_alta", length = 30)
  private String usuarioAlta;
  
  @Column(name = "usuario_baja", length = 30)
  private String usuarioBaja;
  
  @Column(name = "usuario_modificacion", length = 30)
  private String usuarioModificacion;
  
  @OneToMany(mappedBy = "documento")
  @JsonIgnoreProperties({"documento","hibernateLazyInitializer","handler"})
  private List<DocumentoEmpresa> documentoEmpresas;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idtipoempresa")
  @JsonIgnoreProperties({"documentos","hibernateLazyInitializer","handler"})
  private TipoEmpresa tipoEmpresa;
  
  public Documento() {}
  
  public Long getIddocumento( ) { return iddocumento; }
  
  public void setIddocumento(Long iddocumento) { this.iddocumento = iddocumento; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Timestamp getFechaAlta( ) { return this.fechaAlta; }
  
  public void setFechaAlta(Timestamp fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Timestamp getFechaBaja( ) { return this.fechaBaja; }
  
  public void setFechaBaja(Timestamp fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Timestamp getFechaModificacion( ) { return this.fechaModificacion; }
  
  public void setFechaModificacion(Timestamp fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }
  
  public String getNombreDocumento( ) { return this.nombreDocumento; }
  
  public void setNombreDocumento(String nombreDocumento) { this.nombreDocumento = nombreDocumento; }
  
  public String getObligatorio( ) { return this.obligatorio; }
  
  public void setObligatorio(String obligatorio) { this.obligatorio = obligatorio; }
  
  public String getAlias( ) { return alias; }
  
  public void setAlias(String alias) { this.alias = alias; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public void setDocumentoEmpresas(List<DocumentoEmpresa> documentoEmpresas) {
    this.documentoEmpresas = documentoEmpresas;
  }
  
  public void setTipoEmpresa(TipoEmpresa tipoEmpresa) { this.tipoEmpresa = tipoEmpresa; }
  
  public TipoEmpresa getTipoEmpresa( ) { return tipoEmpresa; }
  
}