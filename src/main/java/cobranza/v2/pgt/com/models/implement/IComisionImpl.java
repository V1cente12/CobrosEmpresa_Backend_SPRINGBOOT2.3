
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.dao.IComisionDao;
import cobranza.v2.pgt.com.models.entity.Comision;
import cobranza.v2.pgt.com.models.services.IComisionServ;

@Service
public class IComisionImpl implements
                           IComisionServ {
  
  @Autowired
  private IComisionDao comisionDao;
  
  @Override
  public Optional<Comision> BuscarIdempresaNullvendor(Long idempresa,
                                                      String estado) {
    return comisionDao.findByIdempresaAndEstadoAndVendorIsNull(idempresa, estado);
  }

  @Override
  public List<Comision> obtenerComisiones(String estado) {
    return comisionDao.findAllByEstadoAndVendorIsNull(estado);
  }

  @Override
  public Optional<Comision> BuscarVendor(String vendor,
                                         String estado) {
    return comisionDao.findByVendorAndEstado(vendor, estado);
  }
  
  @Override
  public Comision guardar(Comision entiry) { return comisionDao.save(entiry); }

}
