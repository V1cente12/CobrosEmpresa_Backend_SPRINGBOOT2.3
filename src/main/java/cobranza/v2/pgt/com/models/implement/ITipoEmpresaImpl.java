
package cobranza.v2.pgt.com.models.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.dao.ITipoEmpresaDao;
import cobranza.v2.pgt.com.models.entity.TipoEmpresa;
import cobranza.v2.pgt.com.models.services.ITipoEmpresaServ;

@Service
public class ITipoEmpresaImpl implements ITipoEmpresaServ {
  
  @Autowired
  private ITipoEmpresaDao tipoempresaDao;
  
  @Override
  public Iterable<TipoEmpresa> listarAll(String estado) { return tipoempresaDao.findByEstado(estado); }
  
}
