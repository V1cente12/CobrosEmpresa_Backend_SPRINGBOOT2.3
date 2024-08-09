
package cobranza.v2.pgt.com.models.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IKioscoDao;
import cobranza.v2.pgt.com.models.entity.Kiosko;
import cobranza.v2.pgt.com.models.services.IKioscoServ;

@Service
public class IKioscoImpl implements
                         IKioscoServ {
  
  @Autowired
  private IKioscoDao kioscoDao;
  
  @Override
  @Transactional(readOnly = true)
  public List<Kiosko> listarIdusuario(Long idusuario) { return kioscoDao.findByIdusuario(idusuario); }
  
}
