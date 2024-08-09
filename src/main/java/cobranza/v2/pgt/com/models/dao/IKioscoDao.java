
package cobranza.v2.pgt.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Kiosko;

@Repository
public interface IKioscoDao extends
                            JpaRepository<Kiosko, Long> {
  
  @Query(value = "SELECT k.* FROM bff.kiosko k INNER JOIN bff.kiosko_usuario ku ON k.idkiosko=ku.idkiosko WHERE ku.idusuario=:idusuario", nativeQuery = true)
  public List<Kiosko> findByIdusuario(@Param("idusuario") Long idusuario);
  
}
