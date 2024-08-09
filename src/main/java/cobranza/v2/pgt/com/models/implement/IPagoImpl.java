
package cobranza.v2.pgt.com.models.implement;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.dao.IPagoDao;
import cobranza.v2.pgt.com.models.entity.Pagos;
import cobranza.v2.pgt.com.models.services.IPagoServ;

@Service
public class IPagoImpl implements
                       IPagoServ {
  
  @Autowired
  private IPagoDao pagoDao;
  
  @Override
  @Transactional
  public Pagos obtenerIddeuda(Long id) { return pagoDao.obternerIddeuda(id); }
  
  @Override
  @Transactional
  public Pagos guardar(Pagos pago) { return pagoDao.save(pago); }
  
}
