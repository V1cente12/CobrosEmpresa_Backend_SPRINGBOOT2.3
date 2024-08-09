package cobranza.v2.pgt.com.dto;

import cobranza.v2.pgt.com.models.entity.Empresas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaEmpresaDto {
    private Long idCuentaEmpresa;
    private Long idEmpresa;
    private Integer idBanco;
    private Long idTitular;
    private String banco;
    private Long numeroCuenta;
    private String monedaCuenta;
    private String tipoCuenta;
    private String nombreTitular;
    private String numeroDocumento;
    private String estado;
}
