
package cobranza.v2.pgt.com.models.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.ICyberSouerceDao;
import cobranza.v2.pgt.com.models.entity.CyberSource;
import cobranza.v2.pgt.com.models.services.ICyberSourceServ;

@Service
public class ICyberSourceImpl implements ICyberSourceServ {
  
  @Autowired
  private ICyberSouerceDao cyberDao;
  
  @Override
  @Transactional(readOnly = true)
  public List<CyberSource> obtenerIdRecibo(Long idrecibo) {
    return cyberDao.findByIdrecibo(idrecibo);
  }
}
