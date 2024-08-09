
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.ItemVenta;

@Service
public interface IItemVentaServ {
  
  public List<ItemVenta> listarEstadoIdempresa(String estado,
                                               Long idempresa);
  public Page<ItemVenta> listarEstadoIdempresa(String estado,
                                               Long idempresa,
                                               Pageable p);
  public ItemVenta guardar(ItemVenta iv);
  public ItemVenta guardar(ItemVenta iv,
                           MultipartFile imagen);
  public ItemVenta modificar(ItemVenta nuevo,
                             ItemVenta anterior);
  public Optional<ItemVenta> obtener(Long id);
  public void eliminarId(String estado,
                         Long id);
  public boolean existeNombreImagen(String imagen);
  public boolean existeCodigoAux(String codigoaux);
  public List<ItemVenta> BuscarUnidadIdempresa(String unidad,
                                               Long idempresa,
                                               String estado);
  public void aumentarVentas(Long id);
  public void disminuirVentas(Long id);
}
