package cobranza.v2.pgt.com.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class CargaMasivaParaReporteDTO {

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
    private Long idCargaMasivaDetalle;

    @ApiModelProperty(position = 11)
    private String estadoDetalle;

    @ApiModelProperty(position = 12)
    private String tipoTransaccion;

    @ApiModelProperty(position = 13)
    private String tipoDocumento;

    @ApiModelProperty(position = 14)
    private String numeroDocumento;

    @ApiModelProperty(position = 15)
    private String nombreBeneficiario;

    @ApiModelProperty(position = 16)
    private String periodoAbono;

    @ApiModelProperty(position = 17)
    private String monedaAbono;

    @ApiModelProperty(position = 18)
    private String monto;

    @ApiModelProperty(position = 19)
    private String banco;

    @ApiModelProperty(position = 20)
    private String numeroCuentaDestino;

    @ApiModelProperty(position = 21)
    private String referencia;

    @ApiModelProperty(position = 22)
    private String concepto;
}
