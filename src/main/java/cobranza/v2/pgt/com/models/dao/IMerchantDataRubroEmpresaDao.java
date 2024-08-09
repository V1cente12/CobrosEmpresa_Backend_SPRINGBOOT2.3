
package cobranza.v2.pgt.com.models.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import cobranza.v2.pgt.com.models.entity.AtcMerchantDataRubroEmpresa;

public interface IMerchantDataRubroEmpresaDao
  extends JpaRepository<AtcMerchantDataRubroEmpresa, Long> {

		@Query(value = "SELECT * FROM pgt.atc_merchant_data_rubro_empresa WHERE idempresa=:idempresa", nativeQuery = true)
		List<AtcMerchantDataRubroEmpresa> listaIdEmpresa(@Param("idempresa") Long idempresa);
}
