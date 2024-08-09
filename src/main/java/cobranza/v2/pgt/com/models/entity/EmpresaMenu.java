
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "empresa_menu", schema = "pgt")
public class EmpresaMenu implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idemp_men;
  
  private String estado;
  
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idempresa")
  @JsonIgnoreProperties({"idrecurso","hijo","idpadre","idcliente","atcMerchantDataRubroEmpresas","hibernateLazyInitializer","handler"})
  private Empresas idempresa;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idrecurso")
  @JsonIgnoreProperties({"idempresa","padre","hibernateLazyInitializer","handler"})
  private Recursos idrecurso;
  
  public Long getIdemp_men( ) { return idemp_men; }
  
  public void setIdemp_men(Long idemp_men) { this.idemp_men = idemp_men; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
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
  
  public Recursos getIdrecurso( ) { return idrecurso; }
  
  public void setIdrecurso(Recursos idrecurso) { this.idrecurso = idrecurso; }
  
  /**
   * 
   */
  private static final long serialVersionUID = -1651132054214074188L;
  
}
