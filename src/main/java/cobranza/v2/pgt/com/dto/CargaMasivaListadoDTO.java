package cobranza.v2.pgt.com.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargaMasivaListadoDTO {

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

    @ApiModelProperty(position = 6)
    private Boolean exportado;

    @ApiModelProperty(position = 7)
    private String estado;

    @ApiModelProperty(position = 8)
    private Date fechaAlta;

    @ApiModelProperty(position = 9)
    private Date fechaModificacion;

    @ApiModelProperty(position = 10)
    private Date fechaBaja;

    @ApiModelProperty(position = 11)
    private String usuarioAlta;

    @ApiModelProperty(position = 12)
    private String usuarioModificacion;

    @ApiModelProperty(position = 13)
    private String usuarioBaja;

    @ApiModelProperty(position = 14)
    private List<CargaMasivaDetalleListadoDTO> detalle;
}
