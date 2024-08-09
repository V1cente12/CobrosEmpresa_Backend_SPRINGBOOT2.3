
package cobranza.v2.pgt.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.entity.Unidad;

@Repository
public interface IUnidadDao extends
                            JpaRepository<Unidad, Long> {
  
  public List<Unidad> findByEstadoAndIdempresa(String estado,
                                               Long idempresa);
  public Page<Unidad> findByEstadoAndIdempresa(String estado,
                                               Long idempresa,
                                               Pageable p);
  public List<Unidad> findByCodigoAndIdempresaAndEstado(String codigo,
                                                        Long idempresa,
                                                        String estado);
  @Transactional
  @Modifying
  @Query(value = "update pgt.unidad set estado=:estado,fecha_baja=now() where idunidad=:idunidad", nativeQuery = true)
  public void cambioEstadoId(@Param("estado") String estado,
                             @Param("idunidad") Long idunidad);
  public Optional<Unidad> findByCodigo(String codigo);
  
}
