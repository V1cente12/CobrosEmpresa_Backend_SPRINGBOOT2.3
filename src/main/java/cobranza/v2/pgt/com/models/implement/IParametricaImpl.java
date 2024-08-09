
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IParametricaDao;
import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.services.IParametricaServ;

@Service
public class IParametricaImpl implements
                              IParametricaServ {
  
  @Autowired
  private IParametricaDao parametricaDao;
  
  @Override
  @Transactional(readOnly = true)
  public Parametrica obtener(String dominio,
                             String subdominio,
                             String codigo) {
    return parametricaDao.obtener(dominio, subdominio, codigo);
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> ListaParametrica(String dominio,
                                  String subdominio,
                                  String codigo) {
    List<?> par = parametricaDao.lista(dominio, subdominio, codigo);
    return par;
  }
  
  @Override
  @Transactional
  public Parametrica guardar(Parametrica para) { return parametricaDao.save(para); }
  
  @Override
  @Transactional(readOnly = true)
  public List<Parametrica> ListarDominioSubdominio(String dominio,
                                                   String subdominio) {
    return parametricaDao.findByDominioAndSubdominio(dominio, subdominio);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Parametrica> obtenerDominioSubDominioCodigoInIdEmpresa(String dominio,
                                                                         String subdominio,
                                                                         String codigo) {
    return parametricaDao.obtenerDominioSubDominioCodigoInIdEmpresa(dominio, subdominio, codigo);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Parametrica> obtenerParametricaDominoSubdominioCodigo(String dominio,
                                                         String subdominio,
                                                         String codigo) {
    return parametricaDao.getParametricaByDominioAndSubdominioAndCodigo(dominio, subdominio, codigo);
  }

  @Override
  public List<Parametrica> obtenerComisionesEmpresa(){
    String dominio = "COMISION";
    String subdominio = "EMPRESA";
    return parametricaDao.findByDominioAndSubdominio(dominio, subdominio);

  }
  
}
