
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import org.springframework.stereotype.Service;
import cobranza.v2.pgt.com.models.entity.AtcMerchantDataRubroEmpresa;

@Service
public interface IAtcMerchantDataRubroEmpresaServ {

		public List<AtcMerchantDataRubroEmpresa> findByIdempresa(Long idempresa);

		public AtcMerchantDataRubroEmpresa guardar(AtcMerchantDataRubroEmpresa entity);
}
