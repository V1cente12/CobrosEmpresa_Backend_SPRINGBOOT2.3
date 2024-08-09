
package cobranza.v2.pgt.com.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LiquidacionGeneralQueryDto {
  
  private List<LiquidacionQueryDto> vLiquidacionQueryDtoList;
  
  private BigDecimal montoTotalTarjetaBs;
  private BigDecimal montoTotalTarjetaUsd;
  private BigDecimal montoTotalQrBs;
  private BigDecimal montoTotalQrUsd;
  
  private BigDecimal totalComisionTarjetaPtBs;
  private BigDecimal totalComisionTarjetaPtUsd;
  private BigDecimal totalComisionQrPtBs;
  private BigDecimal totalComisionQrPtUsd;
  
  private BigDecimal totalComisionTarjetaAtBs;
  private BigDecimal totalComisionTarjetaAtUsd;
  private BigDecimal totalComisionQrAtBs;
  private BigDecimal totalComisionQrAtUsd;
  
  private BigDecimal liquidoPagableTarjetaBs;
  private BigDecimal liquidoPagableTarjetaSus;
  private BigDecimal liquidoPagableQrBs;
  private BigDecimal liquidoPagableQrSus;
  
  private BigDecimal totalComisionTarjetaBs;
  private BigDecimal totalComisionTarjetaUsd;
  private BigDecimal totalComisionQrBs;
  private BigDecimal totalComisionQrUsd;
  
  public LiquidacionGeneralQueryDto() {
    this.montoTotalTarjetaBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.montoTotalTarjetaUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.montoTotalQrBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.montoTotalQrUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionTarjetaPtBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionTarjetaPtUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionQrPtBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionQrPtUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionTarjetaAtBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionTarjetaAtUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionQrAtBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionQrAtUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.montoTotalTarjetaBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.liquidoPagableTarjetaBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.liquidoPagableTarjetaSus = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.liquidoPagableQrBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.liquidoPagableQrSus = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionTarjetaBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionTarjetaUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionQrBs = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    this.totalComisionQrUsd = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
  }
}
