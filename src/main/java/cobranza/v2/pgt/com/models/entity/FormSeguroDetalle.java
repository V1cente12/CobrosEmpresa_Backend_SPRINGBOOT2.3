package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "form_seguro_detalle", schema = "bff")
@Data
public class FormSeguroDetalle implements
Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -4112550004603373444L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idformsegdet;
  private String beneficiario;
  private String parentesco;
  private Integer porcentaje;
  private String estado;
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAlta;
  @PrePersist
  public void fecha_alta( ) { this.fechaAlta = new Date( ); }
  private String usuarioAlta;
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaBaja;
  private String usuarioBaja;
  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @JoinColumn(name = "idformseg")
  @JsonIgnoreProperties({"detalle","hibernateLazyInitializer","handler"})
  private FormSeguro idformseg;
}
