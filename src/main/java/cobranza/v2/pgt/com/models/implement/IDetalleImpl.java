
package cobranza.v2.pgt.com.models.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IDetalleDao;
import cobranza.v2.pgt.com.models.entity.Detalle;
import cobranza.v2.pgt.com.models.services.IDetalleServ;

@Service
public class IDetalleImpl implements
                          IDetalleServ {
  
  @Autowired
  private IDetalleDao detalleDao;
  
  @Override
  @Transactional
  public List<Detalle> saveAll(List<Detalle> detalle) {
    return ( List<Detalle> ) detalleDao.saveAll(detalle);
  }
  
  @Override
  @Transactional
  public Detalle save(Detalle detalle) { return detalleDao.save(detalle); }
  
  @Override
  @Transactional
  public Long max( ) { return detalleDao.maxid( ); }
  
  @Override
  public void bajaIdrecibo(Long idrecibo) { detalleDao.bajaIdrecibo(idrecibo); }
  
  @Override
  @Transactional(readOnly = true)
  public List<Detalle> BuscarCodItem(String codItem) { return detalleDao.findByCodItem(codItem); }
  
}
