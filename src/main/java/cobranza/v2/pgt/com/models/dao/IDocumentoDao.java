
package cobranza.v2.pgt.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Documento;

@Repository
public interface IDocumentoDao extends JpaRepository<Documento, Long> {
  
  @Query(value = "SELECT * FROM pgt.documento WHERE estado=:estado", nativeQuery = true)
  Iterable<Documento> findByEstado(@Param("estado") String estado);
  
  @Query(value = "SELECT * FROM pgt.documento WHERE alias=:alias LIMIT 1", nativeQuery = true)
  Optional<Documento> findByAlias(@Param("alias") String alias);
  
  @Query(value = "SELECT * FROM pgt.documento WHERE idtipoempresa=:idtipoempresa", nativeQuery = true)
  List<Documento> findByIdTipoEmpresa(@Param("idtipoempresa") Long idtipoempresa);
}
