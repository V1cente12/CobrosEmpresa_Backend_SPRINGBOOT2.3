package cobranza.v2.pgt.com.dto;

import cobranza.v2.pgt.com.models.entity.Parametrica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CyberSourceDto {
    private Long idcyber;
    private String req_bill_to_address_country;
    private String card_type_name;
    private Long idrecibo;
    private Parametrica idparametrica;
}
