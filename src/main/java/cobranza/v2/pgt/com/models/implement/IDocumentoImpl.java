
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IDocumentoDao;
import cobranza.v2.pgt.com.models.entity.Documento;
import cobranza.v2.pgt.com.models.services.IDocumentoServ;

@Service
public class IDocumentoImpl implements IDocumentoServ {
  
  @Autowired
  private IDocumentoDao documentoDao;
  
  @Override
  @Transactional(readOnly = true)
  public Iterable<Documento> listarAll(String estado) { return documentoDao.findByEstado(estado); }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Documento> findByAlias(String alias) { return documentoDao.findByAlias(alias); }
  
  @Override
  @Transactional(readOnly = true)
  public List<Documento> findByIdTipoEmpresa(Long id) { return documentoDao.findByIdTipoEmpresa(id); }
}
