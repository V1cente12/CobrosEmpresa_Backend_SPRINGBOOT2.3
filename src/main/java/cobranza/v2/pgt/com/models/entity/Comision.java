
package cobranza.v2.pgt.com.models.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "comision", schema = "pgt")
@Data
@ToString
public class Comision {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idcomision", unique = true, nullable = false)
  private Long idcomision;
  
  @Column(name = "amex_inter_atc", nullable = false, precision = 16, scale = 3)
  private BigDecimal amexInterAtc;
  
  @Column(name = "amex_nac_atc", nullable = false, precision = 16, scale = 3)
  private BigDecimal amexNacAtc;
  
  @Column(name = "qr_banco", nullable = false, precision = 16, scale = 3)
  private BigDecimal qrBanco;
  
  @Column(name = "tarjeta_inter_atc", nullable = false, precision = 16, scale = 3)
  private BigDecimal tarjetaInterAtc;
  
  @Column(name = "tarjeta_nac_atc", nullable = false, precision = 16, scale = 3)
  private BigDecimal tarjetaNacAtc;
  
  @Column(nullable = false, length = 3)
  private String estado;
  
  @Column(name = "fecha_alta", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAlta;
  
  @Column(name = "fecha_baja")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaBaja;
  
  @Column(name = "fecha_modificacion")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificacion;
  
  @Column(name = "idempresa", nullable = false)
  private Long idempresa;
  
  @Column(name = "monto_integracion", nullable = false, precision = 16, scale = 2)
  private BigDecimal montoIntegracion;
  
  @Column(name = "amex_inter_pgt", nullable = false, precision = 16, scale = 3)
  private BigDecimal amexInterPgt;
  
  @Column(name = "amex_nac_pgt", nullable = false, precision = 16, scale = 3)
  private BigDecimal amexNacPgt;
  
  @Column(name = "qr_pgt", nullable = false, precision = 16, scale = 3)
  private BigDecimal qrPgt;
  
  @Column(name = "tarjeta_inter_pgt", nullable = false, precision = 16, scale = 3)
  private BigDecimal tarjetaInterPgt;
  
  @Column(name = "tarjeta_nac_pgt", nullable = false, precision = 16, scale = 3)
  private BigDecimal tarjetaNacPgt;
  
  @Column(name = "usuario_alta", length = 15)
  private String usuarioAlta;
  
  @Column(name = "usuario_baja", length = 15)
  private String usuarioBaja;
  
  @Column(name = "usuario_modificacion", length = 15)
  private String usuarioModificacion;
  
  @Column(length = 255)
  private String vendor;
  
  @PrePersist
  public void fechaalta( ) {
    this.fechaAlta = new Date( );
    this.estado = "A";
  }
  
}