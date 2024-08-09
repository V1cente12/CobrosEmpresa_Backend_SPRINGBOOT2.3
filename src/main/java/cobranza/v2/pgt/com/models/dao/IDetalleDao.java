
package cobranza.v2.pgt.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.entity.Detalle;

@Repository
public interface IDetalleDao extends
                             CrudRepository<Detalle, Long> {
  
  @Query(value = "SELECT (max(iddetalle+1)) FROM pgt.detalle", nativeQuery = true)
  Long maxid( );
  
  @Modifying
  @Query(value = "DELETE FROM pgt.detalle WHERE idrecibo=:idrecibo", nativeQuery = true)
  @Transactional
  void bajaIdrecibo(@Param("idrecibo") Long idrecibo);
  
  public List<Detalle> findByCodItem(String codItem);
  @Query(value = "select d.* from pgt.detalle d where d.idrecibo =:idrecibo", nativeQuery = true)
  public Detalle findByIdrecibo(@Param("idrecibo") Long idrecibo);
  
}
