
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "recibo", schema = "pgt")
public class Recibo implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="idrecibo", unique=true, nullable=false)
  private Long idrecibo;
  private Long idprogramacion;
  private String tipo_recibo;
  private Long nro_recibo;
  private BigDecimal monto;
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha_vencimiento;
  private String estado;
  private BigDecimal monto_interes;
  private BigDecimal monto_cargo;
  private String concepto_recibo;
  private String descripcion_general;
  private String periodo;
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) {
    this.fecha_alta = new Date( );
    setNroTransaccion(java.util.UUID.randomUUID( ));
  }
  
  private String usuario_alta;
  private Date fecha_modificacion;
  private String usuario_modificacion;
  private Integer moneda;
  private String codigo_pago;
  private String reference_number;
  private String glosa1;
  private String glosa2;
  private String glosa3;
  private String glosa4;
  private String glosa5;
  private String glosa6;
  private String glosa7;
  private String glosa8;
  private String suscripcion;
  private String nombre_apellido;
  @GeneratedValue(generator = "myIdStrategy")
  @GenericGenerator(name = "myIdStrategy", strategy = "uuid")
  @Column(name = "nro_transaccion")
  private UUID nroTransaccion;
  @OneToOne(mappedBy = "idrecibo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"idrecibo","iddetalle","hibernateLazyInitializer","handler"})
  private Deuda iddeuda;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idcontrato")
  @JsonIgnoreProperties({"idrecibo","hibernateLazyInitializer","handler"})
  private Contrato idcontrato;
  @OneToMany(mappedBy = "idrecibo", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
  @JsonIgnoreProperties({"idrecibo","iddeuda","hibernateLazyInitializer","handler"})
  private List<Detalle> iddetalle;
  @OneToMany(mappedBy = "idrecibo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"idrecibo","hibernateLazyInitializer","handler"})
  private List<CyberSource> idcyber;
  private String lote;
  
  public Deuda getIddeuda( ) { return iddeuda; }
  
  public void setIddeuda(Deuda iddeuda) { this.iddeuda = iddeuda; }
  
  public List<CyberSource> getIdcyber( ) { return idcyber; }
  
  public void setIdcyber(List<CyberSource> idcyber) { this.idcyber = idcyber; }
  
  public Long getIdrecibo( ) { return idrecibo; }
  
  public void setIdrecibo(Long idrecibo) { this.idrecibo = idrecibo; }
  
  public Long getIdprogramacion( ) { return idprogramacion; }
  
  public void setIdprogramacion(Long idprogramacion) { this.idprogramacion = idprogramacion; }
  
  public String getTipo_recibo( ) { return tipo_recibo; }
  
  public void setTipo_recibo(String tipo_recibo) { this.tipo_recibo = tipo_recibo; }
  
  public Long getNro_recibo( ) { return nro_recibo; }
  
  public void setNro_recibo(Long nro_recibo) { this.nro_recibo = nro_recibo; }
  
  public BigDecimal getMonto( ) { return monto; }
  
  public void setMonto(BigDecimal monto) { this.monto = monto.setScale(2, RoundingMode.HALF_DOWN); }
  
  public Date getFecha_vencimiento( ) { return fecha_vencimiento; }
  
  public void setFecha_vencimiento(Date fecha_vencimiento) { this.fecha_vencimiento = fecha_vencimiento; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public BigDecimal getMonto_interes( ) { return monto_interes; }
  
  public void setMonto_interes(BigDecimal monto_interes) { this.monto_interes = monto_interes; }
  
  public BigDecimal getMonto_cargo( ) { return monto_cargo; }
  
  public void setMonto_cargo(BigDecimal monto_cargo) { this.monto_cargo = monto_cargo; }
  
  public String getConcepto_recibo( ) { return concepto_recibo; }
  
  public void setConcepto_recibo(String concepto_recibo) { this.concepto_recibo = concepto_recibo; }
  
  public String getDescripcion_general( ) { return descripcion_general; }
  
  public void setDescripcion_general(String descripcion_general) {
    this.descripcion_general = descripcion_general;
  }
  
  public String getPeriodo( ) { return periodo; }
  
  public void setPeriodo(String periodo) { this.periodo = periodo; }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_modificacion( ) { return fecha_modificacion; }
  
  public void setFecha_modificacion(Date fecha_modificacion) { this.fecha_modificacion = fecha_modificacion; }
  
  public String getUsuario_modificacion( ) { return usuario_modificacion; }
  
  public void setUsuario_modificacion(String usuario_modificacion) {
    this.usuario_modificacion = usuario_modificacion;
  }
  
  public Integer getMoneda( ) { return moneda; }
  
  public void setMoneda(Integer moneda) { this.moneda = moneda; }
  
  public String getCodigo_pago( ) { return codigo_pago; }
  
  public void setCodigo_pago(String codigo_pago) { this.codigo_pago = codigo_pago; }
  
  public String getGlosa1( ) { return glosa1; }
  
  public void setGlosa1(String glosa1) { this.glosa1 = glosa1; }
  
  public String getGlosa2( ) { return glosa2; }
  
  public void setGlosa2(String glosa2) { this.glosa2 = glosa2; }
  
  public String getGlosa3( ) { return glosa3; }
  
  public void setGlosa3(String glosa3) { this.glosa3 = glosa3; }
  
  public Contrato getIdcontrato( ) { return idcontrato; }
  
  public void setIdcontrato(Contrato idcontrato) { this.idcontrato = idcontrato; }
  
  public String getLote( ) { return lote; }
  
  public void setLote(String lote) { this.lote = lote; }
  
  public List<Detalle> getIddetalle( ) { return iddetalle; }
  
  public void setIddetalle(List<Detalle> iddetalle) { this.iddetalle = iddetalle; }
  
  public String getReference_number( ) { return reference_number; }
  
  public void setReference_number(String reference_number) { this.reference_number = reference_number; }
  
  public String getGlosa4( ) { return glosa4; }
  
  public void setGlosa4(String glosa4) { this.glosa4 = glosa4; }
  
  public String getGlosa5( ) { return glosa5; }
  
  public void setGlosa5(String glosa5) { this.glosa5 = glosa5; }
  
  public String getGlosa6( ) { return glosa6; }
  
  public void setGlosa6(String glosa6) { this.glosa6 = glosa6; }
  
  public String getGlosa7( ) { return glosa7; }
  
  public void setGlosa7(String glosa7) { this.glosa7 = glosa7; }
  
  public String getGlosa8( ) { return glosa8; }
  
  public void setGlosa8(String glosa8) { this.glosa8 = glosa8; }
  
  public String getNombre_apellido( ) { return nombre_apellido; }
  
  public void setNombre_apellido(String nombre_apellido) { this.nombre_apellido = nombre_apellido; }
  
  public UUID getNroTransaccion( ) { return nroTransaccion; }
  
  public void setNroTransaccion(UUID nroTransaccion) { this.nroTransaccion = nroTransaccion; }
  
  public String getSuscripcion( ) { return suscripcion; }
  
  public void setSuscripcion(String suscripcion) { this.suscripcion = suscripcion; }
  
  @Override
  public String toString( ) {
    return "Recibo [idrecibo=" + idrecibo + ", idprogramacion=" + idprogramacion + ", tipo_recibo="
      + tipo_recibo + ", nro_recibo=" + nro_recibo + ", monto=" + monto + ", fecha_vencimiento="
      + fecha_vencimiento + ", estado=" + estado + ", monto_interes=" + monto_interes + ", monto_cargo="
      + monto_cargo + ", concepto_recibo=" + concepto_recibo + ", descripcion_general=" + descripcion_general
      + ", periodo=" + periodo + ", fecha_alta=" + fecha_alta + ", usuario_alta=" + usuario_alta
      + ", fecha_modificacion=" + fecha_modificacion + ", usuario_modificacion=" + usuario_modificacion
      + ", moneda=" + moneda + ", codigo_pago=" + codigo_pago + ", reference_number=" + reference_number
      + ", glosa1=" + glosa1 + ", glosa2=" + glosa2 + ", glosa3=" + glosa3 + ", glosa4=" + glosa4
      + ", glosa5=" + glosa5 + ", glosa6=" + glosa6 + ", glosa7=" + glosa7 + ", glosa8=" + glosa8
      + ", nombre_apellido=" + nombre_apellido + ", iddeuda=" + iddeuda + ", idcontrato=" + idcontrato
      + ", iddetalle=" + iddetalle + ", idcyber=" + idcyber + ", lote=" + lote + "]";
  }
  
  /**
  		 * 
  		 */
  private static final long serialVersionUID = 1L;
}
