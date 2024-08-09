package cobranza.v2.pgt.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Pagos;

@Repository
public interface IPagoDao extends JpaRepository<Pagos, Long> {

    Pagos findByIdpago(Long id);

    @Query(value = "SELECT * FROM pgt.deuda d LEFT JOIN pgt.pago p ON p.idpago=d.idpago WHERE d.iddeuda=:iddeuda", nativeQuery = true)
    Pagos obternerIddeuda(@Param("iddeuda") Long iddeuda);

}
