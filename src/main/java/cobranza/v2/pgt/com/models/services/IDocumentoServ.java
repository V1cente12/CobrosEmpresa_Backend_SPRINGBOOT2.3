
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Documento;

@Service
public interface IDocumentoServ {
  
  public Iterable<Documento> listarAll(String estado);
  public Optional<Documento> findByAlias(String alias);
  public List<Documento> findByIdTipoEmpresa(Long id);
  
}
