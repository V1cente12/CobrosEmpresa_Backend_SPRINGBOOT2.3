
package cobranza.v2.pgt.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.TipoEmpresa;

@Repository
public interface ITipoEmpresaDao extends JpaRepository<TipoEmpresa, Long> {
  
  @Query(value = "SELECT t.* FROM pgt.tipo_empresa t WHERE t.estado=:estado", nativeQuery = true)
  Iterable<TipoEmpresa> findByEstado(@Param("estado") String estado);
}
