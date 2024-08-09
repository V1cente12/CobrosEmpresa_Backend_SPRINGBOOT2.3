
package cobranza.v2.pgt.com.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LiquidacionQueryDto {
  
  private String referenceNumber;
  private String valorDocumento;
  private String tipoDocumento;
  private BigDecimal nroPedido;
  private BigDecimal montoBs;
  private BigDecimal montoUsd;
  private String estado;
  private String fechaVenta;
  private String fechaVencimiento;
  private String fechaPago;
  private String horaPago;
  private String formaPago;
  private String grupoEmpresa;
  private String empresa;
  private Integer idEmpresa;
  private String pais;
  private String tarjeta;
  private BigDecimal descuento;
  private BigDecimal subtotal;
  
  public LiquidacionQueryDto() {
    this.descuento = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.subtotal = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
  }
}
