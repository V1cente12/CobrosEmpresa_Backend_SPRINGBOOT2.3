
package cobranza.v2.pgt.com.models.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class PagoRestApi {
  
  // @NotNull(message = "Se requiere campo token")
  // @ApiModelProperty(notes = "Token generado en la sesion", example = "valor va aqui", required = true)
  // private String Token;
  @NotNull(message = "Se requiere campo idempresa")
  @ApiModelProperty(notes = "ID de la empresa proporcionado por PAGATODO360", example = "null", required = true)
  private Long idempresa;
  @NotNull(message = "Se requiere campo nombres")
  @ApiModelProperty(notes = "Nombre de la persona", example = "Marco Alonso", required = true)
  private String nombres;
  @NotNull(message = "Se requiere campo apellido_paterno")
  @ApiModelProperty(notes = "Apellido paterno de la persona", example = "Arrancibia", required = true)
  private String apellido_paterno;
  @NotNull(message = "Se requiere campo token")
  @ApiModelProperty(notes = "TOKEN de pagatodo360", example = "null", required = true)
  private String token;
  @ApiModelProperty(notes = "parameters 1", example = "null")
  private String PData01;
  
  @ApiModelProperty(notes = "Apellido materno de la persona", required = false)
  private String apellido_materno;
  @NotNull(message = "Se requiere campo valor_documento")
  @ApiModelProperty(notes = "Valor documento de la persona", example = "4692559", required = true)
  private String valor_documento;
  @NotNull(message = "Se requiere campo tipo_documento")
  @ApiModelProperty(notes = "Tipo de documento de la persona. Ej.: 1=CI, 2=Nit, 3=Pasaporte, 4=Doc. Serv. Militar", example = "1", required = true)
  private String tipo_documento;
  @NotNull(message = "Se requiere campo domicilio")
  @ApiModelProperty(notes = "Domicilio de la persona", example = "B/San Julian", required = true)
  private String domicilio;
  @NotNull(message = "Se requiere campo nro_recibo")
  @ApiModelProperty(notes = "Numero de comprobante de la deuda/recibo", example = "10", required = true)
  private Long nro_recibo;
  @NotNull(message = "Se requiere campo descripcion_general")
  @ApiModelProperty(notes = "Razon social de la deuda/recibo", example = "Constructora M&M", required = true)
  private String descripcion_general;
  @NotNull(message = "Se requiere campo nit")
  @ApiModelProperty(notes = "NIT de la razon social", example = "67886542013", required = true)
  private String nit;
  @NotNull(message = "Se requiere campo correo")
  @ApiModelProperty(notes = "Correo de la persona/Razon social", example = "correo@correo.com", required = true)
  private String correo;
  @NotNull(message = "Se requiere campo moneda")
  @ApiModelProperty(notes = "Tipo de moneda de la deuda/recibo. ej.: 1=Bs , 2=$us ", example = "1", required = true)
  private int moneda;
  @NotNull(message = "Se requiere campo monto")
  @ApiModelProperty(notes = "Monto total de la deuda/recibo", example = "300.75", required = true)
  private BigDecimal monto;
  @NotNull(message = "Se requiere campo concepto_recibo")
  @ApiModelProperty(notes = "Concepto de la deuda/recibo", example = "Compras de herramientas", required = true)
  private String concepto_recibo;
  @ApiModelProperty(notes = "Detalle de la deuda/recibo", required = true)
  private List<PagoRestDetalle> detalle;
  
  @ApiModelProperty(notes = "parameters 2", example = "null", required = false)
  private String PData02;
  
  @ApiModelProperty(notes = "parameters 3", example = "null", required = false)
  private String PData03;
  
  @ApiModelProperty(notes = "parameters 4", example = "null", required = false)
  private String PData04;
  
  @ApiModelProperty(notes = "parameters 5", example = "null", required = false)
  private String PData05;
  
  @ApiModelProperty(notes = "parameters 6", example = "null", required = false)
  private String PData06;
  
  @ApiModelProperty(notes = "parameters 7", example = "null", required = false)
  private String PData07;
  
  @ApiModelProperty(notes = "parameters 8", example = "null", required = false)
  private String PData08;
  @ApiModelProperty(notes = "parameters 9", example = "null", required = false)
  private String PData09;
  @ApiModelProperty(hidden = true)
  private String reference_number;
  
  public Long getIdempresa( ) { return idempresa; }
  
  public void setIdempresa(Long idempresa) { this.idempresa = idempresa; }
  
  public String getNombres( ) { return nombres; }
  
  public void setNombres(String nombres) { this.nombres = nombres; }
  
  public String getApellido_paterno( ) { return apellido_paterno; }
  
  public void setApellido_paterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }
  
  public String getApellido_materno( ) { return apellido_materno; }
  
  public void setApellido_materno(String apellido_materno) { this.apellido_materno = apellido_materno; }
  
  public String getValor_documento( ) { return valor_documento; }
  
  public void setValor_documento(String valor_documento) { this.valor_documento = valor_documento; }
  
  public String getTipo_documento( ) { return tipo_documento; }
  
  public void setTipo_documento(String tipo_documento) { this.tipo_documento = tipo_documento; }
  
  public String getDomicilio( ) { return domicilio; }
  
  public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
  
  public Long getNro_recibo( ) { return nro_recibo; }
  
  public void setNro_recibo(Long nro_recibo) { this.nro_recibo = nro_recibo; }
  
  public String getDescripcion_general( ) { return descripcion_general; }
  
  public void setDescripcion_general(String descripcion_general) {
    this.descripcion_general = descripcion_general;
  }
  
  public String getNit( ) { return nit; }
  
  public void setNit(String nit) { this.nit = nit; }
  
  public String getCorreo( ) { return correo; }
  
  public void setCorreo(String correo) { this.correo = correo; }
  
  public int getMoneda( ) { return moneda; }
  
  public void setMoneda(int moneda) { this.moneda = moneda; }
  
  public BigDecimal getMonto( ) { return monto; }
  
  public void setMonto(BigDecimal monto) { this.monto = monto.setScale(2, BigDecimal.ROUND_HALF_UP); }
  
  public String getConcepto_recibo( ) { return concepto_recibo; }
  
  public void setConcepto_recibo(String concepto_recibo) { this.concepto_recibo = concepto_recibo; }
  
  // public String getToken( ) { return Token; }
  //
  // public void setToken(String token) { Token = token; }
  
  public List<PagoRestDetalle> getDetalle( ) { return detalle; }
  
  public void setDetalle(List<PagoRestDetalle> detalle) { this.detalle = detalle; }
  
  public String getPData01( ) { return PData01; }
  
  public void setPData01(String pData01) { PData01 = pData01; }
  
  public String getPData02( ) { return PData02; }
  
  public void setPData02(String pData02) { PData02 = pData02; }
  
  public String getPData03( ) { return PData03; }
  
  public void setPData03(String pData03) { PData03 = pData03; }
  
  public String getPData04( ) { return PData04; }
  
  public void setPData04(String pData04) { PData04 = pData04; }
  
  public String getPData05( ) { return PData05; }
  
  public void setPData05(String pData05) { PData05 = pData05; }
  
  public String getPData06( ) { return PData06; }
  
  public void setPData06(String pData06) { PData06 = pData06; }
  
  public String getPData07( ) { return PData07; }
  
  public void setPData07(String pData07) { PData07 = pData07; }
  
  public String getPData08( ) { return PData08; }
  
  public void setPData08(String pData08) { PData08 = pData08; }
  
  public String getPData09( ) { return PData09; }
  
  public void setPData09(String pData09) { PData09 = pData09; }
  
  public String getToken( ) { return token; }
  
  public void setToken(String token) { this.token = token; }
  
  public String getReference_number( ) { return reference_number; }
  
  public void setReference_number(String reference_number) { this.reference_number = reference_number; }
  
}
