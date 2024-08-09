
package cobranza.v2.pgt.com.models.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IProfileEmpresaDao;
import cobranza.v2.pgt.com.models.entity.AtcProfileEmpresa;
import cobranza.v2.pgt.com.models.services.IProfileEmpresaServ;

@Service
public class IProfileEmpresaImpl implements IProfileEmpresaServ {
  
  @Autowired
  private IProfileEmpresaDao profileDao;
  
  @Override
  @Transactional(readOnly = true)
  public AtcProfileEmpresa obtenerId(Long idprofile) { return profileDao.obtenerID(idprofile); }
  
  @Override
  @Transactional
  public AtcProfileEmpresa save(AtcProfileEmpresa entity) { return profileDao.save(entity); }
  
}
