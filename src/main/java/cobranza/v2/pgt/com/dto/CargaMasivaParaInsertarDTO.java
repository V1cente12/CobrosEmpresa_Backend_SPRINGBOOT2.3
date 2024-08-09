package cobranza.v2.pgt.com.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargaMasivaParaInsertarDTO {

    @ApiModelProperty()
    private Long numeroCliente;

    @ApiModelProperty(position = 1)
    private Long numeroCuenta;

    @ApiModelProperty(position = 2)
    private String anioAbono;

    @ApiModelProperty(position = 3)
    private String mesAbono;

    @ApiModelProperty(position = 4)
    private String diaAbono;

    @ApiModelProperty(position = 5)
    private List<CargaMasivaDetalleParaInsertarDTO> detalle;
}
