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
public class CargaMasivaDetalleDTO {
    @ApiModelProperty(position = 0)
    private Long idCargaMasivaDetalle;

    @ApiModelProperty(position = 1)
    private Long idCargaMasiva;

    @ApiModelProperty(position = 2)
    private Integer idBanco;

    @ApiModelProperty(position = 3)
    private String tipoTransaccion;

    @ApiModelProperty(position = 4)
    private String tipoDocumento;

    @ApiModelProperty(position = 5)
    private String numeroDocumento;

    @ApiModelProperty(position = 6)
    private String nombreBeneficiario;

    @ApiModelProperty(position = 7)
    private String periodoAbono;

    @ApiModelProperty(position = 8)
    private String monedaAbono;

    @ApiModelProperty(position = 9)
    private String monto;

    @ApiModelProperty(position = 10)
    private String numeroCuenta;

    @ApiModelProperty(position = 11)
    private String referencia;

    @ApiModelProperty(position = 12)
    private String concepto;
}
