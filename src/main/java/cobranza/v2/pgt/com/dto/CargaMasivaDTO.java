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
public class CargaMasivaDTO {

    @ApiModelProperty()
    private Long idCargaMasiva;

    @ApiModelProperty(position = 1)
    private Long numeroCliente;

    @ApiModelProperty(position = 2)
    private Long numeroCuenta;

    @ApiModelProperty(position = 3)
    private String anioAbono;

    @ApiModelProperty(position = 4)
    private String mesAbono;

    @ApiModelProperty(position = 5)
    private String diaAbono;
}
