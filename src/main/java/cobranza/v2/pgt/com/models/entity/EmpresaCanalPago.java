
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the empresa_canal_pagos database table.
 * 
 */
@Entity
@Table(name = "empresa_canal_pagos", schema = "pgt")
public class EmpresaCanalPago implements
                              Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idcanal;
  
  @Column(name = "apikey_usuario")
  private String apikeyUsuario;
  
  private String banco;
  
  @Column(name = "canal_pagos")
  private String canalPagos;
  
  @Column(name = "codigo_comercio_banco")
  private String codigoComercioBanco;
  
  private Long idempresa;
  
  private String moneda;
  
  @Column(name = "nro_cuenta_banco")
  private String nroCuentaBanco;
  
  @Column(name = "password_servicio_banco")
  private String passwordServicioBanco;
  
  @Column(name = "tipo_codigo_banco")
  private String tipoCodigoBanco;
  
  @Column(name = "url_callback_1")
  private String urlCallback1;
  
  @Column(name = "url_callback_2")
  private String urlCallback2;
  
  @Column(name = "url_servicio_1")
  private String urlServicio1;
  
  @Column(name = "url_servicio_2")
  private String urlServicio2;
  
  @Column(name = "url_servicio_3")
  private String urlServicio3;
  
  @Column(name = "usa_callback")
  private Boolean usaCallback;
  
  @Column(name = "usa_callback_por_transaccion")
  private Boolean usaCallbackPorTransaccion;
  
  @Column(name = "usuario_servicio_banco")
  private String usuarioServicioBanco;
  
  public EmpresaCanalPago() {}
  
  public Integer getIdcanal( ) { return this.idcanal; }
  
  public void setIdcanal(Integer idcanal) { this.idcanal = idcanal; }
  
  public String getApikeyUsuario( ) { return this.apikeyUsuario; }
  
  public void setApikeyUsuario(String apikeyUsuario) { this.apikeyUsuario = apikeyUsuario; }
  
  public String getBanco( ) { return this.banco; }
  
  public void setBanco(String banco) { this.banco = banco; }
  
  public String getCanalPagos( ) { return this.canalPagos; }
  
  public void setCanalPagos(String canalPagos) { this.canalPagos = canalPagos; }
  
  public String getCodigoComercioBanco( ) { return this.codigoComercioBanco; }
  
  public void setCodigoComercioBanco(String codigoComercioBanco) {
    this.codigoComercioBanco = codigoComercioBanco;
  }
  
  public Long getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public String getMoneda( ) { return this.moneda; }
  
  public void setMoneda(String moneda) { this.moneda = moneda; }
  
  public String getNroCuentaBanco( ) { return this.nroCuentaBanco; }
  
  public void setNroCuentaBanco(String nroCuentaBanco) { this.nroCuentaBanco = nroCuentaBanco; }
  
  public String getPasswordServicioBanco( ) { return this.passwordServicioBanco; }
  
  public void setPasswordServicioBanco(String passwordServicioBanco) {
    this.passwordServicioBanco = passwordServicioBanco;
  }
  
  public String getTipoCodigoBanco( ) { return this.tipoCodigoBanco; }
  
  public void setTipoCodigoBanco(String tipoCodigoBanco) { this.tipoCodigoBanco = tipoCodigoBanco; }
  
  public String getUrlCallback1( ) { return this.urlCallback1; }
  
  public void setUrlCallback1(String urlCallback1) { this.urlCallback1 = urlCallback1; }
  
  public String getUrlCallback2( ) { return this.urlCallback2; }
  
  public void setUrlCallback2(String urlCallback2) { this.urlCallback2 = urlCallback2; }
  
  public String getUrlServicio1( ) { return this.urlServicio1; }
  
  public void setUrlServicio1(String urlServicio1) { this.urlServicio1 = urlServicio1; }
  
  public String getUrlServicio2( ) { return this.urlServicio2; }
  
  public void setUrlServicio2(String urlServicio2) { this.urlServicio2 = urlServicio2; }
  
  public String getUrlServicio3( ) { return this.urlServicio3; }
  
  public void setUrlServicio3(String urlServicio3) { this.urlServicio3 = urlServicio3; }
  
  public Boolean getUsaCallback( ) { return this.usaCallback; }
  
  public void setUsaCallback(Boolean usaCallback) { this.usaCallback = usaCallback; }
  
  public Boolean getUsaCallbackPorTransaccion( ) { return this.usaCallbackPorTransaccion; }
  
  public void setUsaCallbackPorTransaccion(Boolean usaCallbackPorTransaccion) {
    this.usaCallbackPorTransaccion = usaCallbackPorTransaccion;
  }
  
  public String getUsuarioServicioBanco( ) { return this.usuarioServicioBanco; }
  
  public void setUsuarioServicioBanco(String usuarioServicioBanco) {
    this.usuarioServicioBanco = usuarioServicioBanco;
  }
  
  @Override
  public String toString( ) {
    return "EmpresaCanalPago [idcanal=" + idcanal + ", apikeyUsuario=" + apikeyUsuario + ", banco=" + banco
      + ", canalPagos=" + canalPagos + ", codigoComercioBanco=" + codigoComercioBanco + ", idempresa="
      + idempresa + ", moneda=" + moneda + ", nroCuentaBanco=" + nroCuentaBanco + ", passwordServicioBanco="
      + passwordServicioBanco + ", tipoCodigoBanco=" + tipoCodigoBanco + ", urlCallback1=" + urlCallback1
      + ", urlCallback2=" + urlCallback2 + ", urlServicio1=" + urlServicio1 + ", urlServicio2=" + urlServicio2
      + ", urlServicio3=" + urlServicio3 + ", usaCallback=" + usaCallback + ", usaCallbackPorTransaccion="
      + usaCallbackPorTransaccion + ", usuarioServicioBanco=" + usuarioServicioBanco + "]";
  }
  
}