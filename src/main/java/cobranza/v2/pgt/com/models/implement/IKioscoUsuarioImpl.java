
package cobranza.v2.pgt.com.models.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.dao.IKioscoUsuarioDao;
import cobranza.v2.pgt.com.models.entity.KioskoUsuario;
import cobranza.v2.pgt.com.models.services.IKioscoUsuaurioServ;

@Service
public class IKioscoUsuarioImpl implements
                                IKioscoUsuaurioServ {
  
  @Autowired
  private IKioscoUsuarioDao kioscousuarioDao;
  
  @Override
  public List<KioskoUsuario> listarIdusuario(Long idusuario) {
    return kioscousuarioDao.findByIdusuario(idusuario);
  }
}
