
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the documento_empresa database table.
 * 
 */
@Entity
@Table(name = "documento_empresa", schema = "pgt")
public class DocumentoEmpresa implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long iddocemp;
  
  @Lob
  @Column(name = "archivo", length = 1000000)
  private Byte[ ] archivo;
  
  @Column(nullable = false, length = 10)
  private String estado;
  
  @Column(name = "fecha_alta")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAlta;
  
  @PrePersist
  public void fechaalta( ) { this.fechaAlta = new Date( ); }
  
  @Column(name = "fecha_baja")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaBaja;
  
  @Column(name = "fecha_modificacion")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificacion;
  
  @Column(name = "formato_archivo", nullable = false, length = 10)
  private String formatoArchivo;
  
  @Column(name = "nombre_archivo", nullable = false, length = 10)
  private String nombreArchivo;
  
  @Column(name = "usuario_alta", length = 30)
  private String usuarioAlta;
  
  @Column(name = "usuario_baja", length = 30)
  private String usuarioBaja;
  
  @Column(name = "usuario_modificacion", length = 30)
  private String usuarioModificacion;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "iddocumento", nullable = false)
  @JsonIgnoreProperties({"documentoEmpresas","hibernateLazyInitializer","handler"})
  private Documento documento;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idempresa", nullable = false)
  @JsonIgnoreProperties({"documentoEmpresas","hibernateLazyInitializer","handler"})
  private Empresas empresa;
  
  public DocumentoEmpresa() {}
  
  public Long getIddocemp( ) { return iddocemp; }
  
  public void setIddocemp(Long iddocemp) { this.iddocemp = iddocemp; }
  
  public Byte[ ] getArchivo( ) { return archivo; }
  
  public void setArchivo(Byte[ ] archivo) { this.archivo = archivo; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Date getFechaAlta( ) { return fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public Date getFechaBaja( ) { return fechaBaja; }
  
  public void setFechaBaja(Date fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public Date getFechaModificacion( ) { return fechaModificacion; }
  
  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }
  
  public String getFormatoArchivo( ) { return this.formatoArchivo; }
  
  public void setFormatoArchivo(String formatoArchivo) { this.formatoArchivo = formatoArchivo; }
  
  public String getNombreArchivo( ) { return this.nombreArchivo; }
  
  public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
  public String getUsuarioModificacion( ) { return this.usuarioModificacion; }
  
  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }
  
  public Documento getDocumento( ) { return this.documento; }
  
  public void setDocumento(Documento documento) { this.documento = documento; }
  
  public Empresas getEmpresa( ) { return empresa; }
  
  public void setEmpresa(Empresas empresa) { this.empresa = empresa; }
  
  public DocumentoEmpresa(Byte[ ] archivo, String estado, String formatoArchivo,
  String nombreArchivo, Documento documento, Empresas empresa)
  {
    this.archivo = archivo;
    this.estado = estado;
    this.formatoArchivo = formatoArchivo;
    this.nombreArchivo = nombreArchivo;
    this.documento = documento;
    this.empresa = empresa;
  }
  
  public DocumentoEmpresa(String estado, String formatoArchivo, String nombreArchivo,
  Documento documento, Empresas empresa)
  {
    this.estado = estado;
    this.formatoArchivo = formatoArchivo;
    this.nombreArchivo = nombreArchivo;
    this.documento = documento;
    this.empresa = empresa;
  }
  
}