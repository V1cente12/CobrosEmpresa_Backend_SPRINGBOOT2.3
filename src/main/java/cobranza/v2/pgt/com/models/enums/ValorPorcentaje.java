package cobranza.v2.pgt.com.models.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum ValorPorcentaje {
    TJPGT(new BigDecimal("0.010")),
    TJATC(new BigDecimal("0.015")),
    QRPGT(new BigDecimal("0.005")),
    QRATC(new BigDecimal("0.005"));

    private final BigDecimal valor;

    ValorPorcentaje(BigDecimal valor){
        this.valor = valor;
    }

    public BigDecimal getValor(){
        return this.valor.setScale(3, RoundingMode.HALF_UP);
    }
}
