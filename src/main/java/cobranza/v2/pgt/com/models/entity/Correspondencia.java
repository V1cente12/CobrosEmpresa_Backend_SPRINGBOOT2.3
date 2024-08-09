
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * The persistent class for the correspondencia
 * database table.
 * 
 */
@Entity
@Data
@Table(name = "correspondencia", schema = "pgt")
public class Correspondencia implements
                             Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idcorrespondencia;
  
  @Column(name = "correo_oculto", length = 500)
  private String correoOculto;
  @Column(name = "correo_copia", length = 500)
  private String correoCopia;
  
  @Column(length = 300)
  private String remitente;
  @Column(length = 300)
  private String asunto;
  @Column(length = 500)
  private String title;
  @Column(name = "page_header", columnDefinition = "TEXT")
  private String pageHeader;
  @Column(name = "column_header", columnDefinition = "TEXT")
  private String columnHeader;
  @Column(name = "body", columnDefinition = "TEXT")
  private String body;
  @Column(name = "page_footer", columnDefinition = "TEXT")
  private String pageFooter;
  @Column(name = "column_footer", columnDefinition = "TEXT")
  private String columnFooter;
  
  @Column(length = 5)
  private String estado;
  
  @Temporal(TemporalType.DATE)
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @Column(name = "fecha_baja")
  private String fechaBaja;
  
  @Column(name = "usuario_alta", length = 60)
  private String usuarioAlta;
  
  @Column(name = "usuario_baja")
  private String usuarioBaja;
  private Long idempresa;
  
  public Correspondencia() {}
  
  public Long getIdcorrespondencia( ) { return this.idcorrespondencia; }
  
  public void setIdcorrespondencia(Long idcorrespondencia) { this.idcorrespondencia = idcorrespondencia; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public Date getFechaAlta( ) { return this.fechaAlta; }
  
  public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
  
  public String getFechaBaja( ) { return this.fechaBaja; }
  
  public void setFechaBaja(String fechaBaja) { this.fechaBaja = fechaBaja; }
  
  public String getUsuarioAlta( ) { return this.usuarioAlta; }
  
  public void setUsuarioAlta(String usuarioAlta) { this.usuarioAlta = usuarioAlta; }
  
  public String getUsuarioBaja( ) { return this.usuarioBaja; }
  
  public void setUsuarioBaja(String usuarioBaja) { this.usuarioBaja = usuarioBaja; }
  
}