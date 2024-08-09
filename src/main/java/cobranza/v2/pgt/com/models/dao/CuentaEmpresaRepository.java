package cobranza.v2.pgt.com.models.dao;

import cobranza.v2.pgt.com.models.entity.CuentaEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CuentaEmpresaRepository extends JpaRepository<CuentaEmpresa, Long>, JpaSpecificationExecutor<CuentaEmpresa> {

}