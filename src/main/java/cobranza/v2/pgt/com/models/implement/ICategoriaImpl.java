
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.ICategoriaDao;
import cobranza.v2.pgt.com.models.entity.CategoriaItemVenta;
import cobranza.v2.pgt.com.models.services.ICategoriaServ;
import cobranza.v2.pgt.com.utils.excepcions.NotFoundException;

@Service
public class ICategoriaImpl implements
                            ICategoriaServ {
  
  @Autowired
  private ICategoriaDao categoriaDao;
  
  @Override
  @Transactional(readOnly = true)
  public List<CategoriaItemVenta> listarEstado(String estado) {
    return categoriaDao.findByEstadoOrderByIdcategoriaAsc(estado);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<CategoriaItemVenta> listarEstadoIdempresa(String estado,
                                                        Integer idempresa,
                                                        Pageable page) {
    return categoriaDao.findByEstadoAndIdempresa(estado, idempresa, page);
  }
  
  @Override
  @Transactional
  public CategoriaItemVenta guardar(CategoriaItemVenta c) { return categoriaDao.save(c); }
  
  @Override
  @Transactional(readOnly = true)
  public List<CategoriaItemVenta> listarEstadoIdempresa(String estado,
                                                        Integer idempresa) {
    return categoriaDao.findByEstadoAndIdempresaOrderByIdcategoriaAsc(estado, idempresa);
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<CategoriaItemVenta> listarEstadoIdempresaPadre(String estado,
                                                             Long idempresa) {
    return categoriaDao.findByEstadoAndIdempresaAndIdpadre(estado, idempresa);
  }
  
  @Override
  @Transactional
  public CategoriaItemVenta modificar(CategoriaItemVenta nuevo,
                                      CategoriaItemVenta anterior) {
    nuevo.setIdcategoria(anterior.getIdcategoria( ));
    return categoriaDao.save(nuevo);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<CategoriaItemVenta> obtener(Long id) {
    return Optional.of(categoriaDao.findById(id)
                                   .orElseThrow(( ) -> new NotFoundException("No existe la categoria")));
  }
  
  @Override
  public void eliminarId(String estado,
                         Long id) { categoriaDao.cambioEstadoId(estado, id); }
  
  @Override
  @Transactional
  public boolean existeNombreImagen(String imagen) { return categoriaDao.findByImagen(imagen)
                                                                        .isPresent( ); }
  
}
