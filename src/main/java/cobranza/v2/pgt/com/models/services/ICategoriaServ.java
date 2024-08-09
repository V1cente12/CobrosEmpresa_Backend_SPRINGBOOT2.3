
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.CategoriaItemVenta;

@Service
public interface ICategoriaServ {
  
  public List<CategoriaItemVenta> listarEstado(String estado);
  public List<CategoriaItemVenta> listarEstadoIdempresa(String estado,
                                                        Integer idempresa);
  public List<CategoriaItemVenta> listarEstadoIdempresaPadre(String estado,
                                                             Long idempresa);
  public Page<CategoriaItemVenta> listarEstadoIdempresa(String estado,
                                                        Integer idempresa,
                                                        Pageable p);
  public CategoriaItemVenta guardar(CategoriaItemVenta c);
  public CategoriaItemVenta modificar(CategoriaItemVenta nuevo,
                                      CategoriaItemVenta anterior);
  public Optional<CategoriaItemVenta> obtener(Long id);
  public void eliminarId(String estado,
                         Long id);
  public boolean existeNombreImagen(String imagen);
}
