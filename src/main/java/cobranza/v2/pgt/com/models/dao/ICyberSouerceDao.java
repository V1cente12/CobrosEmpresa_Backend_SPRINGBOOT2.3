
package cobranza.v2.pgt.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.CyberSource;

@Repository
public interface ICyberSouerceDao extends CrudRepository<CyberSource, Long> {
  
  @Query(value = "SELECT * FROM pgt.cybersource WHERE idrecibo=:idrecibo ORDER BY idcyber DESC", nativeQuery = true)
  public List<CyberSource> findByIdrecibo(@Param("idrecibo") Long idrecibo);
}
