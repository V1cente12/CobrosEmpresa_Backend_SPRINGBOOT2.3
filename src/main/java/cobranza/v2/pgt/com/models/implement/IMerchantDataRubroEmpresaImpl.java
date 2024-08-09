
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cobranza.v2.pgt.com.models.dao.IMerchantDataRubroEmpresaDao;
import cobranza.v2.pgt.com.models.entity.AtcMerchantDataRubroEmpresa;
import cobranza.v2.pgt.com.models.services.IAtcMerchantDataRubroEmpresaServ;

@Service
public class IMerchantDataRubroEmpresaImpl implements IAtcMerchantDataRubroEmpresaServ {

		@Autowired
		private IMerchantDataRubroEmpresaDao merchantDataRubroEmpresa;

		@Override
		@Transactional(readOnly = true)
		public List<AtcMerchantDataRubroEmpresa> findByIdempresa(Long idempresa) {
				return merchantDataRubroEmpresa.listaIdEmpresa(idempresa);
		}

		@Override
		@Transactional
		public AtcMerchantDataRubroEmpresa guardar(AtcMerchantDataRubroEmpresa entity) {
				return merchantDataRubroEmpresa.save(entity);
		}

}
