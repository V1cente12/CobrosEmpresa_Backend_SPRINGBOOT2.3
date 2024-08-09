
package cobranza.v2.pgt.com.models.implement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.dao.IItemVentaDao;
import cobranza.v2.pgt.com.models.entity.ItemVenta;
import cobranza.v2.pgt.com.models.services.IItemVentaServ;
import cobranza.v2.pgt.com.utils.excepcions.NotFoundException;

@Service
public class IItemVentaImpl implements
                            IItemVentaServ {
  
  @Autowired
  private IItemVentaDao itemdao;
  @Value("#{'${path-dir}'}")
  private String _PATH;
  @Value("#{'${path-img}'}")
  private String _IMG;
  
  @Override
  @Transactional
  public ItemVenta guardar(ItemVenta iv) { return itemdao.save(iv); }
  
  @Override
  @Transactional
  public ItemVenta modificar(ItemVenta nuevo,
                             ItemVenta anterior) {
    nuevo.setIditem(anterior.getIditem( ));
    return itemdao.save(nuevo);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<ItemVenta> obtener(Long id) {
    return Optional.of(itemdao.findById(id)
                              .orElseThrow(( ) -> new NotFoundException("No existe el item")));
  }
  
  @Override
  public void eliminarId(String estado,
                         Long id) { itemdao.cambioEstadoId(estado, id); }
  
  @Override
  @Transactional
  public boolean existeNombreImagen(String imagen) { return itemdao.findByImagen(imagen)
                                                                   .isPresent( ); }
  
  @Override
  @Transactional
  public boolean existeCodigoAux(String codigoaux) { return itemdao.findByCodigoAux(codigoaux)
                                                                   .isPresent( ); }
  
  @Override
  public List<ItemVenta> BuscarUnidadIdempresa(String unidad,
                                               Long idempresa,
                                               String estado) {
    return itemdao.findByUnidadAndIdempresaAndEstado(unidad, idempresa, estado);
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<ItemVenta> listarEstadoIdempresa(String estado,
                                               Long idempresa) {
    return itemdao.findByEstadoAndIdempresa(estado, idempresa);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<ItemVenta> listarEstadoIdempresa(String estado,
                                               Long idempresa,
                                               Pageable p) {
    return itemdao.findByEstadoAndIdempresa(estado, idempresa, p);
  }
  
  @Override
  public void aumentarVentas(Long id) { itemdao.aumentarVentas(id); }
  
  @Override
  public void disminuirVentas(Long id) { itemdao.disminuirVentas(id); }
  
  @Override
  public ItemVenta guardar(ItemVenta iv,
                           MultipartFile imagen) {
    try {
      String realPathtoUploads = new File("").getAbsolutePath( )
                                             .replace(this._PATH, "") + this._IMG;
      Path folder = Paths.get(realPathtoUploads);
      if (!Files.exists(folder)) { Files.createDirectories(folder); }
      Files.copy(imagen.getInputStream( ), folder.resolve(iv.getImagen( )));
    }catch(Exception err) {
      err.printStackTrace( );
    }
    return itemdao.save(iv);
  }
}
