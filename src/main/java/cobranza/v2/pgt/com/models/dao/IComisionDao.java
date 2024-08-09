
package cobranza.v2.pgt.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Comision;

@Repository
public interface IComisionDao extends
                              JpaRepository<Comision, Long> {
  
  Optional<Comision> findByIdempresaAndEstadoAndVendorIsNull(Long idempresa,
                                                                    String estado);
  Optional<Comision> findByVendorAndEstado(String vendor,
                                                  String estado);
  List<Comision> findAllByEstadoAndVendorIsNull(String estado);
}
