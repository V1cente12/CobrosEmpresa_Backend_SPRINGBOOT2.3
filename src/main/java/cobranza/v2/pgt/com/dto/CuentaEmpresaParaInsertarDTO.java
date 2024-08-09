package cobranza.v2.pgt.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaEmpresaParaInsertarDTO {

    private Long idEmpresa;
    private Integer idBanco;
    private Long numeroCuenta;
    private String monedaCuenta;
    private String tipoCuenta;
    private String titular;
}

