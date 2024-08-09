
package cobranza.v2.pgt.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cobranza.v2.pgt.com.models.entity.DocumentoEmpresa;

public interface IDocumentoEmpresaDao extends JpaRepository<DocumentoEmpresa, Long> {
  
  @Query(value = "SELECT * FROM pgt.documento_empresa WHERE idempresa=:idempresa", nativeQuery = true)
  public Iterable<DocumentoEmpresa> obtenerIdEmpresa(@Param("idempresa") Long idempresa);
  
}
