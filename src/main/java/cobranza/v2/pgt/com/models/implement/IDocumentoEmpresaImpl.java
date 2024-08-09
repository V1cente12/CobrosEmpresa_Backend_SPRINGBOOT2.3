
package cobranza.v2.pgt.com.models.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IDocumentoEmpresaDao;
import cobranza.v2.pgt.com.models.entity.DocumentoEmpresa;
import cobranza.v2.pgt.com.models.services.IDocumentoEmpresaServ;

@Service
public class IDocumentoEmpresaImpl implements IDocumentoEmpresaServ {
  
  @Autowired
  private IDocumentoEmpresaDao docemp;
  
  @Override
  @Transactional
  public DocumentoEmpresa guardar(DocumentoEmpresa entity) { return docemp.save(entity); }
  
  @Override
  @Transactional(readOnly = true)
  public Iterable<DocumentoEmpresa> ObtenerIdEmpresa(Long idempresa) {
    return docemp.obtenerIdEmpresa(idempresa);
  }
  
  @Override
  @Transactional
  public List<DocumentoEmpresa> guardarAll(List<DocumentoEmpresa> entity) { return docemp.saveAll(entity); }
  
}
