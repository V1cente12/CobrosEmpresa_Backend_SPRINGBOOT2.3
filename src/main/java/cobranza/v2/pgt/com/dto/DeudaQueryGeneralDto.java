package cobranza.v2.pgt.com.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Setter
@Getter
public class DeudaQueryGeneralDto {
    private List<DeudaQueryDto> vDeudaQueryDtoList;
    private String vEstadoDeuda;
    private BigDecimal vTotalPagado;
    private BigDecimal vTotalDeuda;
    public DeudaQueryGeneralDto(){
        this.vTotalPagado = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        this.vTotalDeuda = BigDecimal.ZERO. setScale(3, RoundingMode.HALF_UP);
    }
}
