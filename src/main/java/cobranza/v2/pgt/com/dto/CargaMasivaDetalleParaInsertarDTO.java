package cobranza.v2.pgt.com.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargaMasivaDetalleParaInsertarDTO {

    @ApiModelProperty(position = 0)
    private Integer idBanco;

    @ApiModelProperty(position = 1)
    private String tipoTransaccion;

    @ApiModelProperty(position = 2)
    private String tipoDocumento;

    @ApiModelProperty(position = 3)
    private String numeroDocumento;

    @ApiModelProperty(position = 4)
    private String nombreBeneficiario;

    @ApiModelProperty(position = 5)
    private String periodoAbono;

    @ApiModelProperty(position = 6)
    private String monedaAbono;

    @ApiModelProperty(position = 7)
    private String monto;

    @ApiModelProperty(position = 8)
    private String numeroCuenta;

    @ApiModelProperty(position = 9)
    private String referencia;

    @ApiModelProperty(position = 10)
    private String concepto;
}
