
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Unidad;

@Service
public interface IUnidadServ {
  
  public List<Unidad> BuscarEstadoIdempresa(String estado,
                                            Long idempresa);
  public Page<Unidad> PaginaEstadoIdempresa(String estado,
                                            Long idempresa,
                                            Pageable p);
  public Unidad guardar(Unidad u);
  public Unidad modificar(Unidad nuevo,
                          Unidad anterior);
  public Optional<Unidad> obtener(Long id);
  public Optional<Unidad> obtenerCodigo(String codigo);
  public void eliminarId(String estado,
                         Long id);
  public List<Unidad> BuscarCodigoIdempresa(String codigo,
                                            Long idempresa,
                                            String estado);
  
}
