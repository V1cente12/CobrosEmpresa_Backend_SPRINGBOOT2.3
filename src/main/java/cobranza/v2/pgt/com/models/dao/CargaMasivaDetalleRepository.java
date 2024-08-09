package cobranza.v2.pgt.com.models.dao;

import cobranza.v2.pgt.com.models.entity.CargaMasivaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CargaMasivaDetalleRepository extends JpaRepository<CargaMasivaDetalle, Long>, JpaSpecificationExecutor<CargaMasivaDetalle> {

}