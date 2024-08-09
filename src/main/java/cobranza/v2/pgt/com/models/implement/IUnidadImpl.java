
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IUnidadDao;
import cobranza.v2.pgt.com.models.entity.Unidad;
import cobranza.v2.pgt.com.models.services.IUnidadServ;
import cobranza.v2.pgt.com.utils.excepcions.NotFoundException;

@Service
public class IUnidadImpl implements
                         IUnidadServ {
  
  @Autowired
  private IUnidadDao unidadDao;
  
  @Override
  @Transactional(readOnly = true)
  public List<Unidad> BuscarEstadoIdempresa(String estado,
                                            Long idempresa) {
    return unidadDao.findByEstadoAndIdempresa(estado, idempresa);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Unidad> PaginaEstadoIdempresa(String estado,
                                            Long idempresa,
                                            Pageable p) {
    return unidadDao.findByEstadoAndIdempresa(estado, idempresa, p);
  }
  
  @Override
  @Transactional
  public Unidad guardar(Unidad u) { return unidadDao.save(u); }
  
  @Override
  @Transactional
  public Unidad modificar(Unidad nuevo,
                          Unidad anterior) {
    nuevo.setIdunidad(anterior.getIdunidad( ));
    return unidadDao.save(nuevo);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Unidad> obtener(Long id) {
    return Optional.of(unidadDao.findById(id)
                                .orElseThrow(( ) -> new NotFoundException("No existe la unidad")));
  }
  
  @Override
  public void eliminarId(String estado,
                         Long id) { unidadDao.cambioEstadoId(estado, id); }
  
  @Override
  @Transactional(readOnly = true)
  public List<Unidad> BuscarCodigoIdempresa(String codigo,
                                            Long idempresa,
                                            String estado) {
    return unidadDao.findByCodigoAndIdempresaAndEstado(codigo, idempresa, estado);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Unidad> obtenerCodigo(String codigo) { return unidadDao.findByCodigo(codigo); }
  
}
